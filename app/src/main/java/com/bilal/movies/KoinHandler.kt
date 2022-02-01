package com.bilal.movies

import android.app.Application
import com.bilal.movies.data.dataModule
import com.bilal.movies.data.keys
import com.bilal.movies.data.urls
import com.bilal.movies.domain.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
object KoinHandler {

    fun start(application: Application) {
        stopKoin()
        startKoin {
            properties(keys + urls)
            androidContext(application)
            modules(
                listOf(
                    dataModule,
                    domainModule
                )
            )
        }
    }
}