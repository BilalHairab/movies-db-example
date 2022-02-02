package com.bilal.movies.data.datasources.movies.remote

import com.bilal.movies.domain.models.DiscoverMoviesAPIResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
interface MoviesAPI {
    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") key: String,
        @Query("sort_by") query: String,
        @Query("page") page: Int,
    ): Call<DiscoverMoviesAPIResponse>
}