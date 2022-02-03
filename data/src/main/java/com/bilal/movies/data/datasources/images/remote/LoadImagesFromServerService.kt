package com.bilal.movies.data.datasources.images.remote

import com.bilal.movies.domain.base.DataHolder
import com.bilal.movies.domain.base.ResultError

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
@Suppress("BlockingMethodInNonBlockingContext")
class LoadImagesFromServerService internal constructor(
    private val api: ImagesAPI,
) {
    fun loadImage(imageId: String): DataHolder<ByteArray> {
        return try {
            val apiCall = api.getImage("t/p/w500$imageId").execute()
            val inputStream = apiCall.body()?.byteStream()
            val result = inputStream?.readBytes()
            inputStream?.close()
            DataHolder.Success(result ?: byteArrayOf())
        } catch (e: Exception) {
            e.printStackTrace()
            DataHolder.Fail(ResultError.UNKNOWN_ERROR_WITH_SERVER)
        }
    }
}