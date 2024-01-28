package com.composition.damoa.presentation.common.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

fun Bitmap.scaleDown(): Bitmap {
    val quality = if (width > 2048 && height > 2048) {
        30
    } else if (width > 1024 && height > 1024) {
        50
    } else {
        80
    }
    return Bitmap.createScaledBitmap(this, (width * quality), (height * quality), true)
}

fun Bitmap.compress(): Bitmap {
    val outStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 50, outStream)

    val sdBitmap = BitmapFactory.decodeByteArray(outStream.toByteArray(), 0, outStream.toByteArray().size)

    outStream.flush()
    recycle()

    return sdBitmap
}
