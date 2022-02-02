package com.bilal.movies.data.datasources.images.local

import android.content.Context
import java.io.File

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
class FileInternalStorageDataSource internal constructor(private val applicationContext: Context) {

    fun saveFileCache(fileName: String, byteArray: ByteArray) {
        val imageFile = File(applicationContext.cacheDir, fileName)
        imageFile.createNewFile()
        applicationContext.openFileOutput(fileName, Context.MODE_PRIVATE).use { fos ->
            fos.write(byteArray)
        }
    }

    fun loadFileCache(fileName: String): ByteArray {
        return if (File(applicationContext.cacheDir, fileName).exists()) {
            val inputStream = applicationContext.openFileInput(fileName)
            val result = inputStream.readBytes()
            inputStream.close()
            result
        } else {
            byteArrayOf()
        }
    }
}

