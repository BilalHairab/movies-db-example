package com.bilal.movies.data.repo_imp

import com.bilal.movies.data.custom.MovieInDB
import com.bilal.movies.data.datasources.movies.local.MoviesLocalDataSource
import com.bilal.movies.data.datasources.movies.remote.LoadMoviesFromServerService
import com.bilal.movies.domain.base.DataHolder
import com.bilal.movies.domain.models.Movie
import com.bilal.movies.domain.repos.IMovieRepo


/**
 * Created by Bilal Hairab on 02/02/2022.
 */
class MoviesRepoImpl internal constructor(
    private val api: LoadMoviesFromServerService,
    private val db: MoviesLocalDataSource,
) : IMovieRepo {
    override suspend fun loadMovies(
        query: String,
        pageSize: Int,
        pageIndex: Int
    ): DataHolder<ArrayList<Movie>> {
        val cachedMovies = db.loadMovies(query, pageSize, pageIndex).map { it.movie } as ArrayList
        val missingItemsCount = pageSize - cachedMovies.size
        if (missingItemsCount == 0) {
            return DataHolder.Success(cachedMovies)
        }
        val remoteTask = api.loadMovies(query, pageIndex)
        if (remoteTask is DataHolder.Success) {
            val remoteMovies = remoteTask.data.takeLast(missingItemsCount)
            remoteMovies.forEach {
                db.addMovie(MovieInDB(query, it))
                cachedMovies.add(it)
            }
        }
        return DataHolder.Success(cachedMovies)
    }
}