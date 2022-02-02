package com.bilal.movies.data.repo_imp

import com.bilal.movies.data.datasources.images.local.ImagesLocalDataSource
import com.bilal.movies.data.datasources.images.remote.LoadImagesFromServerService
import com.bilal.movies.domain.base.DataHolder
import com.bilal.movies.domain.base.ResultError
import com.bilal.movies.domain.repos.IImageRepo

/**
 * Created by Bilal Hairab on 03/02/2022.
 */
class ImagesRepoImpl internal constructor(
    private val api: LoadImagesFromServerService,
    private val local: ImagesLocalDataSource,
) : IImageRepo {
    override suspend fun loadImage(imageId: String): DataHolder<ByteArray> {
        val cachedImage = local.loadImage(imageId)
        if (cachedImage.isNotEmpty()) {
            return DataHolder.Success(cachedImage)
        }
        val fetchedImage = api.loadImage(imageId)
        if (fetchedImage is DataHolder.Success) {
            local.saveImage(imageId, fetchedImage.data)
            return DataHolder.Success(fetchedImage.data)
        }
        return DataHolder.Fail(ResultError.UNKNOWN_ERROR_WITH_SERVER)
    }
}