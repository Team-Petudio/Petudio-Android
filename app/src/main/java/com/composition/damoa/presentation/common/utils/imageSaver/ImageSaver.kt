package com.composition.damoa.presentation.common.utils.imageSaver

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID


class ImageSaver(
    private val context: Context,
) {
    private val mutex = Mutex()

    suspend fun saveImageFromUrl(
        imageUrl: String,
        imageName: String = "${UUID.randomUUID()}",
    ) {
        withContext(Dispatchers.IO) {
            val bitmap = imageUrl.downloadBitmapFromUrl()
            bitmap.saveBitmapToGallery(imageName)
        }
    }

    private fun String.downloadBitmapFromUrl(): Bitmap {
        val url = URL(this@downloadBitmapFromUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input = connection.inputStream
        return BitmapFactory.decodeStream(input)
    }

    private suspend fun Bitmap.saveBitmapToGallery(imageName: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
        }

        mutex.withLock {
            val uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            val outputStream = context.contentResolver.openOutputStream(uri!!)
            outputStream?.use {
                compress(Bitmap.CompressFormat.PNG, 100, it)
            }
        }
    }
}
