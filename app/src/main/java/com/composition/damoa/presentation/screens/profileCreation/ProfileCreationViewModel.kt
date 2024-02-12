package com.composition.damoa.presentation.screens.profileCreation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.TokenExpired
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.model.PetColor
import com.composition.damoa.data.model.ProfileConcept
import com.composition.damoa.data.model.S3ImageUrls
import com.composition.damoa.data.repository.interfaces.ConceptRepository
import com.composition.damoa.data.repository.interfaces.PetDetectRepository
import com.composition.damoa.data.repository.interfaces.PetRepository
import com.composition.damoa.data.repository.interfaces.S3ImageRepository
import com.composition.damoa.data.repository.interfaces.S3ImageUrlRepository
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.presentation.common.base.BaseUiState.State
import com.composition.damoa.presentation.screens.profileCreation.state.ConceptDetailUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PetInfoUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PetPhotoSelectionUiState
import com.composition.damoa.presentation.screens.profileCreation.state.SelectedImageUiState
import com.composition.damoa.presentation.screens.profileCreation.state.TicketUiState
import com.esafirm.imagepicker.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private var profileConcept: ProfileConcept? = null

    private val _ticketUiState = MutableStateFlow(TicketUiState())

    private val _conceptDetailUiState = MutableStateFlow(ConceptDetailUiState())
    val conceptDetailUiState = _conceptDetailUiState.asStateFlow()

    private val _petPhotoSelectionUiState = MutableStateFlow(PetPhotoSelectionUiState(onPetSelected = ::selectPet))
    val petPhotoSelectionUiState = _petPhotoSelectionUiState.asStateFlow()

    private val _selectedImageUiState = MutableStateFlow(SelectedImageUiState())
    val selectedImageUiState = _selectedImageUiState.asStateFlow()

    private val _petInfoUiState = MutableStateFlow(PetInfoUiState())
    val petInfoUiState = _petInfoUiState.asStateFlow()

    private lateinit var s3ImageUrls: S3ImageUrls

    private val _uiEvent = MutableSharedFlow<UiEvent>(replay = 1)
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        fetchTicket()
        fetchProfileConceptDetail()
        fetchPets()
    }

    private fun fetchTicket() {
        viewModelScope.launch {
            when (val user = userRepository.getUser()) {
                is Success -> _ticketUiState.value = TicketUiState(ticketCount = user.data.ticket)
                NetworkError -> _ticketUiState.value = _ticketUiState.value.copy(state = State.NETWORK_ERROR)
                TokenExpired -> _uiEvent.emit(UiEvent.TOKEN_EXPIRED)
                is Failure, is Unexpected -> _ticketUiState.value = _ticketUiState.value.copy(
                    state = State.NONE
                )
            }
        }
    }

    private fun fetchProfileConceptDetail() {
        viewModelScope.launch {
            _conceptDetailUiState.value = conceptDetailUiState.value.copy(state = State.LOADING)

            when (val conceptDetail = conceptRepository.getConceptDetail(conceptId)) {
                is Success -> _conceptDetailUiState.value = conceptDetailUiState.value.copy(
                    state = State.SUCCESS,
                    conceptDetail = conceptDetail.data
                )

                NetworkError -> _conceptDetailUiState.value = conceptDetailUiState.value.copy(
                    state = State.NETWORK_ERROR
                )

                TokenExpired -> _uiEvent.emit(UiEvent.TOKEN_EXPIRED)
                is Failure, is Unexpected -> _conceptDetailUiState.value = conceptDetailUiState.value.copy(
                    state = State.NONE
                )
            }
        }
    }

    private fun fetchPets() {
        viewModelScope.launch {
            _petPhotoSelectionUiState.value = petPhotoSelectionUiState.value.copy(state = State.LOADING)
            when (val conceptDetail = petRepository.getPets()) {
                is Success -> _petPhotoSelectionUiState.value = petPhotoSelectionUiState.value.copy(
                    state = State.SUCCESS,
                    pets = conceptDetail.data
                )

                NetworkError -> _petPhotoSelectionUiState.value =
                    petPhotoSelectionUiState.value.copy(state = State.NETWORK_ERROR)

                TokenExpired -> _uiEvent.emit(UiEvent.TOKEN_EXPIRED)
                is Failure, is Unexpected -> _petPhotoSelectionUiState.value = petPhotoSelectionUiState.value.copy(
                    state = State.NONE
                )
            }
        }
    }

    private fun selectPet(petId: Long) {
        _petPhotoSelectionUiState.value = petPhotoSelectionUiState.value.copy(selectedPetId = petId)
    }

    fun updatePetName(name: String) {
        _petInfoUiState.value = petInfoUiState.value.copy(petName = name)
    }

    fun updateColor(color: PetColor) {
        _petInfoUiState.value = _petInfoUiState.value.copy(petColor = color)
    }

    fun uploadPetWithPayment() {
        _petInfoUiState.value = _petInfoUiState.value.copy(state = State.LOADING)
        viewModelScope.launch {
            uploadPetImagesToS3()
        }
    }

    private suspend fun uploadPetImagesToS3() {
        val selectImageFiles = selectedImageUiState.value.selectedImageFiles
        when (val preSignedUrlsResult = s3ImageUrlRepository.getPreSignedUrls(selectImageFiles.size)) {
            is Success -> {
                s3ImageUrls = preSignedUrlsResult.data

                if (!uploadPetImagesToS3(selectImageFiles)) {
                    _uiEvent.emit(UiEvent.UNKNOWN_ERROR)
                    s3ImageUrlRepository.deleteS3ImageDirectory(s3ImageUrls.s3DirectoryPath)
                    return
                }

                _petInfoUiState.value = petInfoUiState.value.copy(
                    petPhotoUrls = s3ImageUrls.preSignedImageUrls.map { it.storedImageUrl }
                )

                when (uploadPet()) {
                    is Success -> {
                        _petInfoUiState.value = petInfoUiState.value.copy(state = State.SUCCESS)
                        payment()
                    }

                    NetworkError -> _petInfoUiState.value = petInfoUiState.value.copy(state = State.NETWORK_ERROR)
                    TokenExpired -> _uiEvent.emit(UiEvent.TOKEN_EXPIRED)
                    is Failure, is Unexpected -> _petInfoUiState.value = petInfoUiState.value.copy(state = State.NONE)
                }
            }

            NetworkError -> _uiEvent.emit(UiEvent.NETWORK_ERROR)
            TokenExpired -> _uiEvent.emit(UiEvent.TOKEN_EXPIRED)
            is Failure, is Unexpected -> _uiEvent.emit(UiEvent.UNKNOWN_ERROR)
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
    ): Boolean = when (s3ImageRepository.uploadImage(preSignedImageUrl.pathWithoutHost, imageFile)) {
        is Success -> true
        else -> false
    }

    private suspend fun uploadPet(): ApiResponse<Unit> {
        val petInfoUiState = petInfoUiState.value
        val petColor = petInfoUiState.petColor ?: return Unexpected(Error("[ERROR] PetColor가 null입니다."))
        if (!selectedImageUiState.value.isValidPetPhotoSize()) {
            return Unexpected(Error("[ERROR] PetPhotoUrls 개수가 ${MIN_UPLOAD_PHOTO_SIZE}개 미만이거나 ${MAX_UPLOAD_PHOTO_SIZE}개 초과입니다."))
        }

        return petRepository.uploadPet(
            petName = petInfoUiState.petName,
            petColor = petColor,
            petPhotoUrls = petInfoUiState.petPhotoUrls,
        )
    }

    private suspend fun payment() {
        if (!hasTicket()) {
            _uiEvent.emit(UiEvent.PAYMENT_FAILED_LACK_OF_TICKET)
            return
        }

        /*
        * 결제 로직 구현 요망
        * */
        // TODO(결제 완료 후, PAYMENT_SUCCESS 이벤트를 emit 해주세요.)
    }

    private fun hasTicket(): Boolean {
        return _ticketUiState.value.ticketCount >= 1
    }

    private suspend fun getConcept(conceptId: Long): ProfileConcept? {
        if (profileConcept != null) return profileConcept

        val concepts = conceptRepository.getConcepts()
        if (concepts !is Success) {
            _uiEvent.emit(UiEvent.UNKNOWN_ERROR)
            return null
        }

        return concepts.data
            .find { it.id == conceptId }
            .also { profileConcept = it }
    }

    fun changeToImageSelectLoading() {
        _selectedImageUiState.tryEmit(selectedImageUiState.value.copy(state = State.LOADING))
    }

    fun validatePetImageSize(images: List<Image>): Boolean {
        val originSelectedImageFileSize = selectedImageUiState.value.selectedImageFiles.size
        val imageSize = images.size + originSelectedImageFileSize

        if (imageSize in MIN_UPLOAD_PHOTO_SIZE..MAX_UPLOAD_PHOTO_SIZE) {
            return true
        }

        _uiEvent.tryEmit(UiEvent.INVALID_PET_IMAGE_SIZE)
        return false
    }

    fun detectPetImages(imageFiles: List<File>) {
        val originSelectedImageFiles = selectedImageUiState.value.selectedImageFiles

        viewModelScope.launch {
            val concept = getConcept(conceptId) ?: return@launch

            when (val petDetectResult = petDetectRepository.detectPet(imageFiles, concept.petType)) {
                is Success -> {
                    val (goodImageFiles, badImageFiles) = classifyPetImages(imageFiles, petDetectResult.data)
                    val newSelectedImageUiState = SelectedImageUiState(
                        selectedImageFiles = originSelectedImageFiles + goodImageFiles,
                        badImageFiles = badImageFiles
                    )
                    _selectedImageUiState.emit(newSelectedImageUiState)
                    _uiEvent.emit(UiEvent.PET_DETECT_SUCCESS)
                }

                else -> {
                    _uiEvent.emit(UiEvent.UNKNOWN_ERROR)
                    _selectedImageUiState.emit(selectedImageUiState.value.copy(state = State.NONE))
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

    enum class UiEvent {
        PAYMENT_SUCCESS,
        PAYMENT_FAILED_LACK_OF_TICKET,
        INVALID_PET_IMAGE_SIZE,
        PET_DETECT_SUCCESS,
        TOKEN_EXPIRED,
        NETWORK_ERROR,
        UNKNOWN_ERROR,
    }

    companion object {
        const val KEY_CONCEPT_ID = "key_concept_id"
        private const val MIN_UPLOAD_PHOTO_SIZE = 10
        private const val MAX_UPLOAD_PHOTO_SIZE = 12
    }
}