package com.composition.damoa.presentation.screens.profileCreation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.model.PetColor
import com.composition.damoa.data.repository.interfaces.ConceptRepository
import com.composition.damoa.data.repository.interfaces.PetRepository
import com.composition.damoa.presentation.common.base.BaseUiState.State
import com.composition.damoa.presentation.screens.profileCreation.state.PetInfoUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PetPhotoUiState
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileConceptUiState
import com.esafirm.imagepicker.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileCreationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val conceptRepository: ConceptRepository,
    private val petRepository: PetRepository,
) : ViewModel() {
    private val _conceptDetailUiState = MutableStateFlow(ProfileConceptUiState())
    val conceptDetailUiState = _conceptDetailUiState.asStateFlow()

    private val _petPhotosUiState = MutableStateFlow(PetPhotoUiState())
    val petPhotosUiState = _petPhotosUiState.asStateFlow()

    private val _selectedImages = MutableStateFlow<List<Image>>(emptyList())
    val selectedImages = _selectedImages.asStateFlow()

    private val _petInfoUiState = MutableStateFlow(PetInfoUiState())
    val petInfoUiState = _petInfoUiState.asStateFlow()

    init {
        fetchProfileConceptDetail()
        fetchPets()
    }

    private fun fetchProfileConceptDetail() {
        viewModelScope.launch {
            _conceptDetailUiState.value = conceptDetailUiState.value.copy(state = State.LOADING)
            val conceptId = savedStateHandle.get<Long>(KEY_CONCEPT_ID) ?: return@launch

            when (val conceptDetail = conceptRepository.getConceptDetail(conceptId)) {
                is Success -> _conceptDetailUiState.value = conceptDetailUiState.value.copy(
                    state = State.SUCCESS,
                    profileConceptDetail = conceptDetail.data
                )

                NetworkError -> _conceptDetailUiState.value = conceptDetailUiState.value.copy(
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

                NetworkError -> _petPhotosUiState.value = petPhotosUiState.value.copy(
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

    fun savePet() {
        _petInfoUiState.value = _petInfoUiState.value.copy(state = State.LOADING)
        viewModelScope.launch {
            when (addPet()) {
                is Success -> _petInfoUiState.value = petInfoUiState.value.copy(state = State.SUCCESS)
                NetworkError -> _petInfoUiState.value = petInfoUiState.value.copy(state = State.NETWORK_ERROR)
                is Failure, is Unexpected -> _petInfoUiState.value = petInfoUiState.value.copy(state = State.NONE)
            }
        }
    }

    private suspend fun addPet(): ApiResponse<Unit> {
        val petColor = petInfoUiState.value.petColor ?: return Unexpected(Error("[ERROR] PetColor가 null입니다."))
        if (!validatePetPhotoUrlSize()) return Unexpected(Error("[ERROR] PetPhotoUrls 개수가 10개 미만이거나 12개 초과입니다."))

        return petRepository.addPet(
            petName = petInfoUiState.value.petName,
            petColor = petColor,
            petPhotoUrls = petInfoUiState.value.petPhotoUrls,
        )
    }

    private fun validatePetPhotoUrlSize(): Boolean {
        return petInfoUiState.value.petPhotoUrls.size in 10..12
    }

    companion object {
        const val KEY_CONCEPT_ID = "key_concept_id"
    }
}