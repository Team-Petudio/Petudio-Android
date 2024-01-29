package com.composition.damoa.presentation.screens.profileCreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.model.PetColor
import com.composition.damoa.data.repository.interfaces.ConceptRepository
import com.composition.damoa.data.repository.interfaces.PetRepository
import com.composition.damoa.presentation.common.base.BaseUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PetInfoUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PetPhotoUiState
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileConceptUiState
import com.esafirm.imagepicker.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileCreationViewModel @Inject constructor(
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
            _conceptDetailUiState.value = conceptDetailUiState.value.copy(state = BaseUiState.State.LOADING)
            when (val conceptDetail = conceptRepository.getConceptDetail(1)) {
                is Success -> _conceptDetailUiState.value = conceptDetailUiState.value.copy(
                    state = BaseUiState.State.SUCCESS,
                    profileConceptDetail = conceptDetail.data
                )

                NetworkError -> _conceptDetailUiState.value = conceptDetailUiState.value.copy(
                    state = BaseUiState.State.NETWORK_ERROR
                )

                is Failure, is Unexpected -> _conceptDetailUiState.value = conceptDetailUiState.value.copy(
                    state = BaseUiState.State.NONE
                )
            }
        }
    }

    private fun fetchPets() {
        viewModelScope.launch {
            _petPhotosUiState.value = petPhotosUiState.value.copy(state = BaseUiState.State.LOADING)
            when (val conceptDetail = petRepository.getPets()) {
                is Success -> _petPhotosUiState.value = petPhotosUiState.value.copy(
                    state = BaseUiState.State.SUCCESS,
                    pets = conceptDetail.data
                )

                NetworkError -> _petPhotosUiState.value = petPhotosUiState.value.copy(
                    state = BaseUiState.State.NETWORK_ERROR
                )

                is Failure, is Unexpected -> _petPhotosUiState.value = petPhotosUiState.value.copy(
                    state = BaseUiState.State.NONE
                )
            }
        }
    }

    fun uploadImageFiles(files: List<File>) {

    }

    fun updatePetName(name: String) {
        _petInfoUiState.value = petInfoUiState.value.copy(petName = name)
    }

    fun updateColor(color: PetColor) {
        _petInfoUiState.value = _petInfoUiState.value.copy(petColor = color)
    }
}