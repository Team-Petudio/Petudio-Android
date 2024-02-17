package com.composition.damoa.presentation.common.extensions

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.composition.damoa.R
import com.composition.damoa.presentation.common.utils.permissionRequester.Permission
import com.composition.damoa.presentation.common.utils.permissionRequester.PermissionRequester
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.quality
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes messageRes: Int) {
    Toast.makeText(this, getString(messageRes), Toast.LENGTH_SHORT).show()
}

fun Context.navigateToWebsite(url: String) {
    val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(myIntent)
}

fun Context.navigateToTermOfUse() {
    navigateToWebsite("https://petudio.notion.site/Petudio-57118639803f4588b14b84ee6bc5709e?pvs=4")
}

fun Context.navigateToPrivacy() {
    navigateToWebsite("https://petudio.notion.site/Petudio-f2ead0982d5a46899274bdde710332c2?pvs=4")
}

fun Context.checkPostNotificationPermission(): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
    return ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.POST_NOTIFICATIONS,
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.requestWriteExternalStoragePermission(
    message: String,
    onGranted: () -> Unit,
    onDenied: () -> Unit,
) {
    PermissionRequester().launch(
        context = this,
        permission = Permission.WRITE_EXTERNAL_STORAGE,
        dialogMessage = message,
        onGranted = onGranted,
        onDenied = onDenied,
    )
}

fun Context.showNotification(
    title: String,
    message: String,
    notificationId: Int = System.currentTimeMillis().toInt(),
    channelId: Int,
    intent: Intent = Intent(),
    groupKey: String? = null,
) {
    if (!checkPostNotificationPermission()) return

    val notificationManager = NotificationManagerCompat.from(this)
    val notification = createNotification(
        channelId = channelId,
        title = title,
        message = message,
        notificationId = notificationId,
        intent = intent,
        groupKey = groupKey,
    )
    notificationManager.notify(notificationId, notification)
}

private fun Context.createNotification(
    channelId: Int,
    title: String,
    message: String,
    notificationId: Int,
    intent: Intent = Intent(),
    groupKey: String? = null,
): Notification = NotificationCompat.Builder(this, channelId.toString())
    .setSmallIcon(R.mipmap.ic_launcher_round)
    .setContentTitle(title)
    .setGroupSummary(true)
    .setGroup(groupKey)
    .setContentText(message)
    .setContentIntent(getPendingIntent(intent, notificationId))
    .setAutoCancel(true)
    .build()

private fun Context.getPendingIntent(
    intent: Intent,
    notificationId: Int,
): PendingIntent? = TaskStackBuilder.create(this).run {
    addNextIntentWithParentStack(intent)
    getPendingIntent(
        notificationId,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )
}

suspend fun Context.reduceImageSizeAndCreateFile(
    contentUri: Uri,
    fileName: String,
    quality: Int = 80,
    imageWidth: Int = 512,
): File? = runCatching {
    val imageFile = requireNotNull(createFileFromContentUri(contentUri, "$fileName.jpg")) {
        "[ERROR] content uri로부터 File을 생성하는데 실패했습니다."
    }
    val originalBitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(contentUri))
    val imageHeight = (originalBitmap.height * (imageWidth.toFloat() / originalBitmap.width)).toInt()

    Compressor.compress(this, imageFile) {
        quality(quality)
        default(width = imageWidth, height = imageHeight)
    }
}.getOrNull()

private fun Context.createFileFromContentUri(contentUri: Uri, fileName: String): File? {
    var inputStream: InputStream? = null
    var outputStream: FileOutputStream? = null
    val newFile = File(filesDir, fileName)

    try {
        inputStream = contentResolver.openInputStream(contentUri)
        outputStream = FileOutputStream(newFile)

        inputStream?.copyTo(outputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    } finally {
        inputStream?.close()
        outputStream?.close()
    }

    return newFile
}
