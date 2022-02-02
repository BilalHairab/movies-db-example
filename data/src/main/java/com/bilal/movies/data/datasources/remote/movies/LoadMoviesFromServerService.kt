package com.bilal.movies.data.datasources.remote.movies

import com.bilal.movies.domain.base.DataHolder
import com.bilal.movies.domain.base.ResultError
import com.bilal.movies.domain.models.Movie

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
@Suppress("BlockingMethodInNonBlockingContext")
class LoadMoviesFromServerService internal constructor(
    private val api: MoviesAPI,
    private val apiKey: String
) {
    fun loadMovies(query: String, page: Int): DataHolder<Array<Movie>> {
        val apiCall = api.getMovies(apiKey, query, page).execute()
        val response = apiCall.body()
        response?.run {
            if (apiCall.isSuccessful) {
                return if (this.results.isNullOrEmpty()) {
                    DataHolder.Success(arrayOf())
                } else
                    DataHolder.Success(this.results!!)
            }
        }
        return DataHolder.Fail(ResultError.UNKNOWN_ERROR_WITH_SERVER)
    }
}