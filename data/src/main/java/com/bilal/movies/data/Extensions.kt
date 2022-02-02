package com.bilal.movies.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.nio.ByteBuffer

/**
 * Created by Bilal Hairab on 03/02/2022.
 */

fun ByteArray.toBitmap() = BitmapFactory.decodeByteArray(this, 0, this.size)


fun Bitmap.toByteArray(): ByteArray {
    val size: Int = this.rowBytes * this.height
    val byteBuffer: ByteBuffer = ByteBuffer.allocate(size)
    this.copyPixelsToBuffer(byteBuffer)
    return byteBuffer.array()
}
