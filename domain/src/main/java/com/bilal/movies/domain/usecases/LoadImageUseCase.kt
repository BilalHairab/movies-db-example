package com.bilal.movies.domain.usecases

import com.bilal.movies.domain.base.BaseUseCase
import com.bilal.movies.domain.base.DataHolder
import com.bilal.movies.domain.repos.IImageRepo

/**
 * Created by Bilal Hairab on 03/02/2022.
 */

class LoadImageParams(val imageID: String)

class LoadImageUseCase internal constructor(private val imagesRepo: IImageRepo) :
    BaseUseCase<LoadImageParams>(imagesRepo) {

    override suspend fun execute(params: LoadImageParams): DataHolder<ByteArray> {
        return imagesRepo.loadImage(params.imageID)
    }
}