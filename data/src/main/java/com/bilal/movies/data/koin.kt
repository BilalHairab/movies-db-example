package com.bilal.movies.data

import com.bilal.movies.data.datasources.local.AppDatabase
import com.bilal.movies.data.datasources.local.movie.MoviesLocalDataSource
import com.bilal.movies.data.datasources.remote.movies.LoadMoviesFromServerService
import com.bilal.movies.data.datasources.remote.movies.MoviesAPI
import com.bilal.movies.data.repo_imp.MoviesRepoImpl
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

val baseMoviesUrl = StringQualifier("load-movies-api-url")
val baseMoviesUrlAPIKey = StringQualifier("load-movies-api-key")

val dataModule = module {

    single(baseMoviesUrl) {
        Retrofit.Builder()
            .baseUrl(getBaseUrl(baseMoviesUrl.value))
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

    single {
        AppDatabase.getInstance(context = get())
    }

    factory {
        (get() as AppDatabase).moviesDao()
    }.bind(MoviesLocalDataSource::class)

    factory {
        MoviesRepoImpl(
            api = get(),
            db = get()
        )
    }.bind(IMovieRepo::class)

}

fun Scope.getBaseUrl(propName: String): String = getProperty(propName)