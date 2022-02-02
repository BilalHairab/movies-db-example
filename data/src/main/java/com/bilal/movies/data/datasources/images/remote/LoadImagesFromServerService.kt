package com.bilal.movies.data.datasources.images.remote

import com.bilal.movies.domain.base.DataHolder

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
@Suppress("BlockingMethodInNonBlockingContext")
class LoadImagesFromServerService internal constructor(
    private val api: ImagesAPI,
) {
    fun loadImage(imageId: String): DataHolder<ByteArray> {
        val apiCall = api.getImage(imageId).execute()
        val inputStream = apiCall.body()?.byteStream()
        val result = inputStream?.readBytes()
        inputStream?.close()
        return DataHolder.Success(result ?: byteArrayOf())
    }
}