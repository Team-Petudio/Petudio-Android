package com.composition.damoa.presentation.screens.giftcard.component

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.drawToBitmap
import androidx.lifecycle.lifecycleScope
import com.composition.damoa.R
import com.composition.damoa.data.model.GiftCard
import com.composition.damoa.presentation.common.extensions.showToast
import com.composition.damoa.presentation.common.utils.permissionRequester.Permission
import com.composition.damoa.presentation.common.utils.permissionRequester.PermissionRequester
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun GiftCardDetailDialog(giftCardUiState: GiftCardUiState) {
    if (giftCardUiState.selectedGiftCard != null) {
        GiftCardDetailDialog(
            giftCard = giftCardUiState.selectedGiftCard,
            onDismissClick = { giftCardUiState.onGiftCardDetailDismiss() },
        )
    }
}

@Composable
fun GiftCardDetailDialog(
    giftCard: GiftCard,
    onDismissClick: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = (context as ComponentActivity).lifecycleScope
    val giftCardDetailView = remember { mutableStateOf<View?>(null) }

    Dialog(
        onDismissRequest = onDismissClick,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(0.95F),
            verticalArrangement = Arrangement.Center,
        ) {
            IconButton(onClick = onDismissClick) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    tint = Color.White,
                    contentDescription = null,
                )
            }
            AndroidView(
                factory = { FrameLayout(context).also { giftCardDetailView.value = it } },
                update = { view ->
                    ComposeView(context).apply {
                        setContent { GiftCardDetailContent(giftCard = giftCard) }
                        view.addView(this)
                    }
                }
            )
            GiftCardButtons(
                onCopyClick = { copyGiftCardNumber(context = context, number = giftCard.giftCode) },
                onSaveClick = {
                    val notNullGiftCardDetailView = giftCardDetailView.value ?: return@GiftCardButtons
                    requestSaveImagePermission(
                        context = context,
                        onGranted = {
                            saveViewAsImageInGallery(view = notNullGiftCardDetailView, coroutineScope = coroutineScope)
                        },
                    )
                },
            )
        }
    }
}

private fun copyGiftCardNumber(
    context: Context,
    number: String,
) {
    val clipBoardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("giftCardNumber", number)

    clipBoardManager.setPrimaryClip(clipData)
    context.showToast(R.string.copy_success_message)
}

private fun requestSaveImagePermission(
    context: Context,
    onGranted: () -> Unit,
) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
        onGranted()
        return
    }

    PermissionRequester().launch(
        context = context,
        permission = Permission.WRITE_EXTERNAL_STORAGE,
        dialogMessage = context.getString(R.string.giftcard_save_permission_message),
        onGranted = onGranted,
        onDenied = { context.showToast(R.string.giftcard_save_permission_denied_message) },
    )
}

private fun saveViewAsImageInGallery(
    view: View,
    coroutineScope: CoroutineScope,
    fileName: String = "petudio_${System.currentTimeMillis()}",
    description: String = "petudio giftcard",
) {
    val context = view.context
    val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        coroutineScope.launch {
            context.showToast(R.string.unknown_error_message)
        }
    }

    coroutineScope.launch(Dispatchers.IO + exceptionHandler) {
        val bitmap = view.drawToBitmap().asImageBitmap().asAndroidBitmap()

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                put(MediaStore.Images.Media.DESCRIPTION, description)
            }
        }

        val imageUri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

        val imageOutStream = context.contentResolver.openOutputStream(imageUri!!)!!
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageOutStream)

        imageOutStream.flush()
        imageOutStream.close()

        withContext(Dispatchers.Main) {
            context.showToast(R.string.image_save_success_message)
        }
    }
}