package com.bilal.movies

import android.app.Application
import com.bilal.movies.KoinHandler

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinHandler.start(this)
    }
}