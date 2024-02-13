package com.composition.damoa.presentation.common.utils.permissionRequester

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build

enum class Permission {
    READ_EXTERNAL_STORAGE,
    WRITE_EXTERNAL_STORAGE,
    POST_NOTIFICATION;

    @SuppressLint("InlinedApi")
    fun getPermission(): String = when (this) {
        READ_EXTERNAL_STORAGE -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        WRITE_EXTERNAL_STORAGE -> Manifest.permission.WRITE_EXTERNAL_STORAGE
        POST_NOTIFICATION -> Manifest.permission.POST_NOTIFICATIONS
    }
}