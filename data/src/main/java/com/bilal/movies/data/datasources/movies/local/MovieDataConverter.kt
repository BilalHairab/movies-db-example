package com.bilal.movies.data.datasources.movies.local

import androidx.room.TypeConverter
import com.bilal.movies.domain.models.Movie
import com.google.gson.Gson

/**
 * Created by Bilal Hairab on 03/02/2022.
 */
class MovieDataConverter {
    @TypeConverter
    fun movieToString(movie: Movie): String {
        return Gson().toJson(movie)
    }

    @TypeConverter
    fun storedStringToMovie(value: String): Movie {
        return Gson().fromJson(value, Movie::class.java)
    }
}