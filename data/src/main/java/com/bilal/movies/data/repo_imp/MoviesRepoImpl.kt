package com.bilal.movies.data.repo_imp

import com.bilal.movies.data.datasources.remote.weather.LoadMoviesFromServerService
import com.bilal.movies.domain.base.DataHolder
import com.bilal.movies.domain.models.Movie
import com.bilal.movies.domain.repos.IMovieRepo


/**
 * Created by Bilal Hairab on 02/02/2022.
 */
class MoviesRepoImpl internal constructor(
    private val api: LoadMoviesFromServerService,
) : IMovieRepo {
    override suspend fun loadMovies(
        query: String, page: Int
    ): DataHolder<Array<Movie>> {
        return api.loadMovies(query, page)
    }
}