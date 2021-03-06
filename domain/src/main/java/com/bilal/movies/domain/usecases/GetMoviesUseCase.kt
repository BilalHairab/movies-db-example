package com.bilal.movies.domain.usecases

import com.bilal.movies.domain.base.BaseUseCase
import com.bilal.movies.domain.base.DataHolder
import com.bilal.movies.domain.models.Movie
import com.bilal.movies.domain.repos.IMovieRepo

/**
 * Created by Bilal Hairab on 02/02/2022.
 */

enum class MoviesSortTypes(val query: String) {
    POPULARITY_DESC("popularity.desc"),
    RATED_DESC("vote_average.desc"),
    REVENUE_DESC("revenue.desc"),
}

class GetMoviesParams(val sortType: MoviesSortTypes, val pageSize: Int, val pageIndex: Int)

class GetMoviesUseCase internal constructor(private val moviesRepo: IMovieRepo) :
    BaseUseCase<GetMoviesParams>(moviesRepo) {

    override suspend fun execute(params: GetMoviesParams): DataHolder<ArrayList<Movie>> {
        return moviesRepo.loadMovies(params.sortType.query, params.pageSize, params.pageIndex)
    }
}