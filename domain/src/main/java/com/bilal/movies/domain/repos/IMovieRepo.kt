package com.bilal.movies.domain.repos

import com.bilal.movies.domain.base.BaseRepository
import com.bilal.movies.domain.base.DataHolder
import com.bilal.movies.domain.models.Movie

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
interface IMovieRepo : BaseRepository{
    suspend fun loadMovies(query: String, page: Int): DataHolder<List<Movie>>
}