package com.bilal.movies.domain.models

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
class DiscoverMoviesAPIResponse constructor(
    var page:Int?,
    var results: Array<Movie>?,
    var total_results: Int?,
    var total_pages: Int?
)