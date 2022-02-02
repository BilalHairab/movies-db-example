package com.bilal.movies.data.datasources.images.local

/**
 * Created by Bilal Hairab on 03/02/2022.
 */
class ImagesLocalLoader internal constructor(val internalStorageDataSource: FileInternalStorageDataSource) :
    ImagesLocalDataSource {
    override fun saveImage(imageId: String, byteArray: ByteArray) {
        internalStorageDataSource.saveFileCache(imageId, byteArray)
    }

    override fun loadImage(imageId: String): ByteArray {
        return internalStorageDataSource.loadFileCache(imageId)
    }
}