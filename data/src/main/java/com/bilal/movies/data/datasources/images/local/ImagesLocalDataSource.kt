package com.bilal.movies.data.datasources.images.local

import android.graphics.Bitmap

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
interface ImagesLocalDataSource {
    fun saveImage(imageId: String, byteArray: ByteArray)

    fun loadImage(imageId: String): ByteArray
}