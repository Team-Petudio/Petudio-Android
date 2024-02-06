package com.composition.damoa.presentation.common.utils.permissionRequester

import android.content.Context
import com.composition.damoa.R
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission


class PermissionRequester {
    private var onGranted: () -> Unit = {}
    private var onDenied: () -> Unit = {}

    private val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            onGranted()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            onDenied()
        }
    }

    fun launch(
        context: Context,
        dialogTitle: String = context.getString(R.string.permission_request_title),
        dialogMessage: String,
        permission: Permission,
        onGranted: () -> Unit,
        onDenied: () -> Unit,
    ) {
        this.onGranted = onGranted
        this.onDenied = onDenied

        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setDeniedTitle(dialogTitle)
            .setDeniedMessage(dialogMessage)
            .setPermissions(permission.getPermission())
            .check()
    }
}