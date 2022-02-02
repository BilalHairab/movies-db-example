package com.bilal.movies.data.datasources.local.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bilal.movies.data.custom.MovieInDB
import com.bilal.movies.domain.models.Movie

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
@Dao
interface MoviesDao : MoviesLocalDataSource{
    @Query("SELECT * FROM movies WHERE category IS (:category) LIMIT :pageSize OFFSET :pageIndex")
    override fun loadMovies(category: String, pageSize: Int, pageIndex: Int): List<MovieInDB>

    @Insert
    override fun addMovie(movie: Movie)
}