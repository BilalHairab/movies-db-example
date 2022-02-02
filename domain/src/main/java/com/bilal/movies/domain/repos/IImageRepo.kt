package com.bilal.movies.domain.repos

import com.bilal.movies.domain.base.BaseRepository
import com.bilal.movies.domain.base.DataHolder

/**
 * Created by Bilal Hairab on 03/02/2022.
 */
interface IImageRepo : BaseRepository {
    suspend fun loadImage(imageId: String): DataHolder<ByteArray>
}