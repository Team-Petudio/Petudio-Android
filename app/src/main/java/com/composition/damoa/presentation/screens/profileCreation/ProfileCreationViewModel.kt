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
import com.composition.damoa.data.repository.interfaces.ConceptRepository
import com.composition.damoa.data.repository.interfaces.PetRepository
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.presentation.common.base.BaseUiState.State
import com.composition.damoa.presentation.screens.profileCreation.state.ConceptDetailUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PetInfoUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PetPhotoUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PointUiState
import com.esafirm.imagepicker.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileCreationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val conceptRepository: ConceptRepository,
    private val petRepository: PetRepository,
//    private val paymentRepository: PaymentRepository,
) : ViewModel() {
    private val conceptId = requireNotNull(savedStateHandle.get<Long>(KEY_CONCEPT_ID))

    private val _pointUiState = MutableStateFlow(PointUiState())

    private val _conceptDetailUiState = MutableStateFlow(ConceptDetailUiState())
    val conceptDetailUiState = _conceptDetailUiState.asStateFlow()

    private val _petPhotosUiState = MutableStateFlow(PetPhotoUiState())
    val petPhotosUiState = _petPhotosUiState.asStateFlow()

    private val _selectedImages = MutableStateFlow<List<Image>>(emptyList())
    val selectedImages = _selectedImages.asStateFlow()

    private val _petInfoUiState = MutableStateFlow(PetInfoUiState())
    val petInfoUiState = _petInfoUiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        fetchPoint()
        fetchProfileConceptDetail()
        fetchPets()
    }

    private fun fetchPoint() {
        viewModelScope.launch {
            when (val user = userRepository.getUser()) {
                is Success -> _pointUiState.value = PointUiState(point = user.data.point)
                NetworkError, TokenExpired -> _pointUiState.value = _pointUiState.value.copy(
                    state = State.NETWORK_ERROR
                )

                is Failure, is Unexpected -> _pointUiState.value = _pointUiState.value.copy(
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

                NetworkError, TokenExpired -> _conceptDetailUiState.value = conceptDetailUiState.value.copy(
                    state = State.NETWORK_ERROR
                )

                is Failure, is Unexpected -> _conceptDetailUiState.value = conceptDetailUiState.value.copy(
                    state = State.NONE
                )
            }
        }
    }

    private fun fetchPets() {
        viewModelScope.launch {
            _petPhotosUiState.value = petPhotosUiState.value.copy(state = State.LOADING)
            when (val conceptDetail = petRepository.getPets()) {
                is Success -> _petPhotosUiState.value = petPhotosUiState.value.copy(
                    state = State.SUCCESS,
                    pets = conceptDetail.data
                )

                NetworkError, TokenExpired -> _petPhotosUiState.value = petPhotosUiState.value.copy(
                    state = State.NETWORK_ERROR
                )

                is Failure, is Unexpected -> _petPhotosUiState.value = petPhotosUiState.value.copy(
                    state = State.NONE
                )
            }
        }
    }

    fun updatePetName(name: String) {
        _petInfoUiState.value = petInfoUiState.value.copy(petName = name)
    }

    fun updateColor(color: PetColor) {
        _petInfoUiState.value = _petInfoUiState.value.copy(petColor = color)
    }

    fun addPetWithPayment() {
        _petInfoUiState.value = _petInfoUiState.value.copy(state = State.LOADING)
        viewModelScope.launch {
            when (addPet(petInfoUiState.value)) {
                is Success -> {
                    _petInfoUiState.value = petInfoUiState.value.copy(state = State.SUCCESS)
                    payment()
                }

                NetworkError, TokenExpired -> _petInfoUiState.value =
                    petInfoUiState.value.copy(state = State.NETWORK_ERROR)

                is Failure, is Unexpected -> _petInfoUiState.value = petInfoUiState.value.copy(state = State.NONE)
            }
        }
    }

    private suspend fun addPet(petInfoUiState: PetInfoUiState): ApiResponse<Unit> {
        val petColor = petInfoUiState.petColor ?: return Unexpected(Error("[ERROR] PetColor가 null입니다."))
        if (!petInfoUiState.isValidPetPhotoSize()) return Unexpected(Error("[ERROR] PetPhotoUrls 개수가 10개 미만이거나 12개 초과입니다."))

        return petRepository.addPet(
            petName = petInfoUiState.petName,
            petColor = petColor,
            petPhotoUrls = petInfoUiState.petPhotoUrls,
        )
    }

    private suspend fun payment() {
        if (satisfyPaymentPoint()) {
            _uiEvent.emit(UiEvent.PAYMENT_FAILED_LACK_OF_COIN)
            return
        }

        // TODO(결제 완료 후, PAYMENT_SUCCESS 이벤트를 emit 해주세요.)
    }

    private suspend fun satisfyPaymentPoint(): Boolean {
        val concepts = conceptRepository.getConcepts()
        if (concepts !is Success) return false

        val concept = concepts.data.find { it.id == conceptId } ?: return false
        return _pointUiState.value.point >= concept.discountedPoint
    }

    enum class UiEvent {
        PAYMENT_SUCCESS,
        PAYMENT_FAILED_LACK_OF_COIN,
    }

    companion object {
        const val KEY_CONCEPT_ID = "key_concept_id"
    }
}