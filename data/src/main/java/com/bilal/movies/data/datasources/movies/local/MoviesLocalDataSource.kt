package com.bilal.movies.data.datasources.movies.local

import com.bilal.movies.data.custom.MovieInDB
import com.bilal.movies.domain.models.Movie

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
interface MoviesLocalDataSource {
    fun loadMovies(category: String, pageSize: Int, pageIndex: Int): List<MovieInDB>

    fun addMovie(movie: Movie)
}