package com.composition.damoa.presentation.screens.profileCreation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composition.damoa.data.model.PetColor
import com.composition.damoa.data.model.ProfileConcept
import com.composition.damoa.data.model.S3ImageUrls
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.network.retrofit.callAdapter.Failure
import com.composition.damoa.data.network.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.network.retrofit.callAdapter.Success
import com.composition.damoa.data.network.retrofit.callAdapter.TokenExpired
import com.composition.damoa.data.network.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.repository.interfaces.ConceptRepository
import com.composition.damoa.data.repository.interfaces.PetDetectRepository
import com.composition.damoa.data.repository.interfaces.PetRepository
import com.composition.damoa.data.repository.interfaces.S3ImageRepository
import com.composition.damoa.data.repository.interfaces.S3ImageUrlRepository
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.presentation.common.base.BaseUiState.State
import com.composition.damoa.presentation.screens.profileCreation.screen.creationIntroduce.state.ProfileCreationIntroduceUiState
import com.composition.damoa.presentation.screens.profileCreation.screen.payment.state.PaymentUiState
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.state.PetSelectionUiState
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.state.PhotoUploadUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PetInfoUiState
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent
import com.esafirm.imagepicker.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ProfileCreationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val conceptRepository: ConceptRepository,
    private val petRepository: PetRepository,
    private val s3ImageUrlRepository: S3ImageUrlRepository,
    private val s3ImageRepository: S3ImageRepository,
    private val petDetectRepository: PetDetectRepository,
//    private val paymentRepository: PaymentRepository,
) : ViewModel() {
    private val conceptId = requireNotNull(savedStateHandle.get<Long>(KEY_CONCEPT_ID))

    private val _paymentUiState = MutableStateFlow(
        PaymentUiState(onPaymentClick = ::payment)
    )
    val paymentUiState = _paymentUiState.asStateFlow()

    private val _profileCreationIntroduceUiState = MutableStateFlow(ProfileCreationIntroduceUiState())
    val profileCreationIntroduceUiState = _profileCreationIntroduceUiState.asStateFlow()

    private val _petSelectionUiState = MutableStateFlow(
        PetSelectionUiState(
            onPetSelected = ::selectPet
        )
    )
    val petSelectionUiState = _petSelectionUiState.asStateFlow()

    private val _photoUploadUiState = MutableStateFlow(
        PhotoUploadUiState(onUnselectImage = ::unselectPetImage)
    )
    val selectedImageUiState = _photoUploadUiState.asStateFlow()

    private val _petInfoUiState = MutableStateFlow(
        PetInfoUiState(
            onPetNameChanged = ::updatePetName,
            onPetColorSelected = ::updateColor,
            onPetUploadClick = ::uploadPetImages
        )
    )
    val petInfoUiState = _petInfoUiState.asStateFlow()

    private lateinit var s3ImageUrls: S3ImageUrls

    private val _event = MutableSharedFlow<ProfileCreationUiEvent>(replay = 1)
    val event = _event.asSharedFlow()

    init {
        fetchTicket()
        fetchProfileConceptDetail()
        fetchPets()
    }

    private fun fetchTicket() {
        viewModelScope.launch {
            when (val user = userRepository.getUser()) {
                is Success -> _paymentUiState.update { it.copy(ticketCount = user.data.ticketCount) }
                NetworkError -> _paymentUiState.update { it.copy(state = State.NETWORK_ERROR) }
                TokenExpired -> _event.emit(ProfileCreationUiEvent.TOKEN_EXPIRED)
                is Failure, is Unexpected -> _paymentUiState.update { it.copy(state = State.NONE) }
            }
        }
    }

    private fun fetchProfileConceptDetail() {
        viewModelScope.launch {
            val profileConcept = getProfileConcept(conceptId) ?: return@launch
            _profileCreationIntroduceUiState.update { it.copy(state = State.LOADING) }

            when (val conceptDetail = conceptRepository.getProfileConceptDetail(conceptId)) {
                is Success -> _profileCreationIntroduceUiState.update {
                    it.copy(state = State.SUCCESS, conceptDetail = conceptDetail.data, profileConcept = profileConcept)
                }

                NetworkError -> _profileCreationIntroduceUiState.update { it.copy(state = State.NETWORK_ERROR) }
                TokenExpired -> _event.emit(ProfileCreationUiEvent.TOKEN_EXPIRED)
                is Failure, is Unexpected -> _profileCreationIntroduceUiState.update { it.copy(state = State.NONE) }
            }
        }
    }

    private fun fetchPets() {
        viewModelScope.launch {
            _petSelectionUiState.update { it.copy(state = State.LOADING) }
            when (val conceptDetail = petRepository.getPets()) {
                is Success -> _petSelectionUiState.update { it.copy(state = State.SUCCESS, pets = conceptDetail.data) }
                NetworkError -> _petSelectionUiState.update { it.copy(state = State.NETWORK_ERROR) }
                TokenExpired -> _event.emit(ProfileCreationUiEvent.TOKEN_EXPIRED)
                is Failure, is Unexpected -> _petSelectionUiState.update { it.copy(state = State.NONE) }
            }
        }
    }

    private fun selectPet(petId: Long) {
        _petSelectionUiState.update { it.copy(selectedPetId = petId) }
    }

    private fun updatePetName(name: String) {
        _petInfoUiState.update { it.copy(petName = name) }
    }

    private fun updateColor(color: PetColor) {
        _petInfoUiState.update { it.copy(petColor = color) }
    }

    private fun uploadPetImages() {
        _petInfoUiState.update { it.copy(state = State.LOADING) }
        viewModelScope.launch { uploadPetImagesToS3() }
    }

    private suspend fun uploadPetImagesToS3() {
        val selectedImageFiles = selectedImageUiState.value.selectedImageFiles

        when (val preSignedUrlsResult = s3ImageUrlRepository.getPreSignedUrls(selectedImageFiles.size)) {
            is Success -> {
                s3ImageUrls = preSignedUrlsResult.data

                if (!uploadPetImagesToS3(selectedImageFiles)) {
                    _event.emit(ProfileCreationUiEvent.UNKNOWN_ERROR)
                    s3ImageUrlRepository.deleteS3ImageDirectory(s3ImageUrls.s3DirectoryPath)
                    return
                }

                _petInfoUiState.update {
                    it.copy(uploadedPetPhotoUrls = s3ImageUrls.preSignedImageUrls.map { preSignedImageUrl ->
                        preSignedImageUrl.storedImageUrl
                    })
                }

                when (uploadPet()) {
                    is Success -> {
                        _petInfoUiState.update { it.copy(state = State.SUCCESS) }
                        _event.emit(ProfileCreationUiEvent.UPLOAD_PET_SUCCESS)
                    }

                    NetworkError -> {
                        _petInfoUiState.update { it.copy(state = State.NETWORK_ERROR) }
                        _event.emit(ProfileCreationUiEvent.NETWORK_ERROR)
                    }

                    TokenExpired -> _event.emit(ProfileCreationUiEvent.TOKEN_EXPIRED)
                    is Failure, is Unexpected -> {
                        _petInfoUiState.update { it.copy(state = State.NONE) }
                        _event.emit(ProfileCreationUiEvent.UNKNOWN_ERROR)
                    }
                }
            }

            NetworkError -> _event.emit(ProfileCreationUiEvent.NETWORK_ERROR)
            TokenExpired -> _event.emit(ProfileCreationUiEvent.TOKEN_EXPIRED)
            is Failure, is Unexpected -> _event.emit(ProfileCreationUiEvent.UNKNOWN_ERROR)
        }
    }

    private suspend fun uploadPetImagesToS3(
        imageFiles: List<File>,
    ): Boolean = viewModelScope.async {
        s3ImageUrls.preSignedImageUrls
            .zip(imageFiles)
            .map { (preSignedImageUrl, imageFile) -> async { uploadPetImageToS3(preSignedImageUrl, imageFile) } }
            .awaitAll()
            .all { result -> result }
    }.await()

    private suspend fun uploadPetImageToS3(
        preSignedImageUrl: S3ImageUrls.PreSignedImageUrl,
        imageFile: File,
    ): Boolean = s3ImageRepository.uploadImage(preSignedImageUrl.pathWithoutHost, imageFile) is Success

    private suspend fun uploadPet(): ApiResponse<Unit> {
        val petInfoUiState = petInfoUiState.value
        val petColor = petInfoUiState.petColor ?: return Unexpected(Error("[ERROR] PetColor가 null입니다."))
        if (!selectedImageUiState.value.isValidPetPhotoSize()) {
            return Unexpected(Error("[ERROR] PetPhotoUrls 개수가 ${MIN_UPLOAD_PHOTO_SIZE}개 미만이거나 ${MAX_UPLOAD_PHOTO_SIZE}개 초과입니다."))
        }

        return petRepository.uploadPet(
            petName = petInfoUiState.petName,
            petColor = petColor,
            petPhotoUrls = petInfoUiState.uploadedPetPhotoUrls,
        )
    }

    private fun payment() {
        viewModelScope.launch {
            if (!hasTicket()) {
                _event.emit(ProfileCreationUiEvent.PAYMENT_FAILED_LACK_OF_TICKET)
                return@launch
            }

            /*
            * 결제 로직 구현 요망
            * */
            // TODO(결제 완료 후, PAYMENT_SUCCESS 이벤트를 emit 해주세요.)
        }
    }

    private fun hasTicket(): Boolean {
        return _paymentUiState.value.ticketCount >= 1
    }

    private suspend fun getProfileConcept(conceptId: Long): ProfileConcept? {
        val concept = conceptRepository.getProfileConcept(conceptId)
        return if (concept is Success) concept.data else null
    }

    fun changeToImageSelectLoading() {
        _photoUploadUiState.update { it.copy(state = State.LOADING) }
    }

    fun validatePetImageSize(images: List<Image>): Boolean {
        val originSelectedImageFileSize = selectedImageUiState.value.selectedImageFiles.size
        val imageSize = images.size + originSelectedImageFileSize

        if (imageSize in MIN_UPLOAD_PHOTO_SIZE..MAX_UPLOAD_PHOTO_SIZE) {
            return true
        }

        _event.tryEmit(ProfileCreationUiEvent.INVALID_PET_IMAGE_SIZE)
        return false
    }

    fun detectPetImages(imageFiles: List<File>) {
        val originSelectedImageFiles = selectedImageUiState.value.selectedImageFiles

        viewModelScope.launch {
            val profileConcept = _profileCreationIntroduceUiState.value.profileConcept ?: return@launch

            when (val petDetectResult = petDetectRepository.detectPet(imageFiles, profileConcept.petType)) {
                is Success -> {
                    val (goodImageFiles, badImageFiles) = classifyPetImages(imageFiles, petDetectResult.data)
                    _photoUploadUiState.update {
                        it.copy(
                            selectedImageFiles = originSelectedImageFiles + goodImageFiles,
                            badImageFiles = badImageFiles
                        )
                    }
                    _event.emit(ProfileCreationUiEvent.PET_DETECT_SUCCESS)
                }

                else -> {
                    _event.emit(ProfileCreationUiEvent.UNKNOWN_ERROR)
                    _photoUploadUiState.update { it.copy(state = State.NONE) }
                }
            }
        }
    }

    private fun classifyPetImages(
        imageFiles: List<File>,
        petDetectData: List<Boolean>,
    ): Pair<List<File>, List<File>> {
        val goodImageFiles = mutableListOf<File>()
        val badImageFiles = mutableListOf<File>()

        petDetectData.forEachIndexed { index, isPet ->
            if (isPet) goodImageFiles.add(imageFiles[index])
            else badImageFiles.add(imageFiles[index])
        }
        return Pair(goodImageFiles, badImageFiles)
    }

    private fun unselectPetImage(imageFile: File) {
        val unselectedImageFiles = selectedImageUiState.value.selectedImageFiles - imageFile
        _photoUploadUiState.update { it.copy(selectedImageFiles = unselectedImageFiles) }
    }

    companion object {
        const val KEY_CONCEPT_ID = "key_concept_id"
        private const val MIN_UPLOAD_PHOTO_SIZE = 10
        private const val MAX_UPLOAD_PHOTO_SIZE = 12
    }
}