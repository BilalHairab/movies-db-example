package com.bilal.movies.data

import org.koin.core.qualifier.StringQualifier
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Bilal Hairab on 02/02/2022.
 */

val baseMoviesUrl = StringQualifier("load-movies-api-url")
val baseMoviesUrlAPIKey = StringQualifier("load-movies-api-key")

val dataModule = module {

    single(baseMoviesUrl) {
        Retrofit.Builder()
            .baseUrl(getBaseUrl(baseMoviesUrl.value))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}

fun Scope.getBaseUrl(propName: String): String = getProperty(propName)