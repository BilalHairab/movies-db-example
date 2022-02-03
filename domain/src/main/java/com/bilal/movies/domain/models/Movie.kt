package com.bilal.movies.domain.models

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
class Movie constructor(
    var poster_path: String,
    var adult: Boolean?,
    var overview: String?,
    var release_date: String?,
    var genre_ids: Array<Int>?,
    var id: Int?,
    var original_title: String?,
    var original_language: String?,
    var title: String?,
    var backdrop_path: String?,
    var popularity: Number?,
    var vote_count: Int?,
    var video: Boolean?,
    var vote_average: Number?,
)