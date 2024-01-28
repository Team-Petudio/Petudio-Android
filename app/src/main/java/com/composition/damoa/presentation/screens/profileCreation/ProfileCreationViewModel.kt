package com.composition.damoa.presentation.screens.profileCreation

import androidx.lifecycle.ViewModel
import com.esafirm.imagepicker.model.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class ProfileCreationViewModel : ViewModel() {
    private val _selectedImages = MutableStateFlow<List<Image>>(emptyList())
    val selectedImages = _selectedImages.asStateFlow()

    fun uploadImageFiles(files: List<File>) {

    }
}