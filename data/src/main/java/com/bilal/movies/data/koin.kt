package com.bilal.movies.data

import com.bilal.movies.data.datasources.images.local.FileInternalStorageDataSource
import com.bilal.movies.data.datasources.images.local.ImagesLocalDataSource
import com.bilal.movies.data.datasources.images.local.ImagesLocalLoader
import com.bilal.movies.data.datasources.images.remote.ImagesAPI
import com.bilal.movies.data.datasources.images.remote.LoadImagesFromServerService
import com.bilal.movies.data.datasources.movies.local.AppDatabase
import com.bilal.movies.data.datasources.movies.local.MoviesLocalDataSource
import com.bilal.movies.data.datasources.movies.remote.LoadMoviesFromServerService
import com.bilal.movies.data.datasources.movies.remote.MoviesAPI
import com.bilal.movies.data.repo_imp.ImagesRepoImpl
import com.bilal.movies.data.repo_imp.MoviesRepoImpl
import com.bilal.movies.domain.repos.IImageRepo
import com.bilal.movies.domain.repos.IMovieRepo
import org.koin.core.qualifier.StringQualifier
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Bilal Hairab on 02/02/2022.
 */

val baseImagesUrl = StringQualifier("load-images-api-url")
val baseMoviesUrl = StringQualifier("load-movies-api-url")
val baseMoviesUrlAPIKey = StringQualifier("load-movies-api-key")

val dataModule = module {

    single(baseMoviesUrl) {
        Retrofit.Builder()
            .baseUrl(getBaseUrl(baseMoviesUrl.value))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single(baseImagesUrl) {
        Retrofit.Builder()
            .baseUrl(getBaseUrl(baseImagesUrl.value))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory {
        val api = get<Retrofit>(baseMoviesUrl).create(MoviesAPI::class.java)
        LoadMoviesFromServerService(
            api,
            getProperty(baseMoviesUrlAPIKey.value)
        )
    }

    factory {
        val api = get<Retrofit>(baseImagesUrl).create(ImagesAPI::class.java)
        LoadImagesFromServerService(
            api
        )
    }

    single {
        AppDatabase.getInstance(context = get())
    }

    single {
        FileInternalStorageDataSource(applicationContext = get())
    }

    factory {
        (get() as AppDatabase).moviesDao()
    }.bind(MoviesLocalDataSource::class)

    factory {
        ImagesLocalLoader(internalStorageDataSource = get())
    }.bind(ImagesLocalDataSource::class)

    factory {
        MoviesRepoImpl(
            api = get(),
            db = get()
        )
    }.bind(IMovieRepo::class)

    factory {
        ImagesRepoImpl(
            api = get(),
            local = get()
        )
    }.bind(IImageRepo::class)
}

fun Scope.getBaseUrl(propName: String): String = getProperty(propName)