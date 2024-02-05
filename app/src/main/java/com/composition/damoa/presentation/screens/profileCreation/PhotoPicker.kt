package com.composition.damoa.presentation.screens.profileCreation

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.intl.Locale
import com.composition.damoa.R
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.ImagePickerSavePath
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image

class PhotoPicker(activity: ComponentActivity) {
    private var onResult: (List<Image>) -> Unit = {}
    private val photoPickerLauncher = activity.registerImagePicker { images ->
        onResult(images)
    }

    fun launchPhotoPicker(
        context: Context,
        maxSelectSize: Int = 12,
        onResult: (List<Image>) -> Unit,
    ) {
        this.onResult = onResult
        val config = ImagePickerConfig {
            mode = ImagePickerMode.MULTIPLE
            language = Locale.current.language
            theme = R.style.ImagePickerTheme
            arrowColor = Color.White.toArgb()
            imageTitle = context.getString(R.string.app_name)
            doneButtonText = context.getString(R.string.done)
            limit = maxSelectSize
            savePath = ImagePickerSavePath("petudio")
            ImagePickerConfig()
            isIncludeAnimation = true
        }
        photoPickerLauncher.launch(config)
    }
}