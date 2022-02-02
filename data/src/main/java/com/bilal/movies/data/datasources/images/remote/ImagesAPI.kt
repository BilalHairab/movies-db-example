package com.bilal.movies.data.datasources.images.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
interface ImagesAPI {
    @GET
    fun getImage(
        @Url imageId: String,
    ): Call<ResponseBody>
}