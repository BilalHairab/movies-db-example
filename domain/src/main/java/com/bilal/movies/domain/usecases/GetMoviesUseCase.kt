package com.bilal.movies.domain.usecases

import com.bilal.movies.domain.base.BaseUseCase
import com.bilal.movies.domain.base.DataHolder
import com.bilal.movies.domain.models.Movie
import com.bilal.movies.domain.repos.IMovieRepo

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
class GetMoviesParams(val query: String, val page: Int)

class GetMoviesUseCase internal constructor(private val moviesRepo: IMovieRepo) :
    BaseUseCase<GetMoviesParams>(moviesRepo) {

    override suspend fun execute(params: GetMoviesParams): DataHolder<List<Movie>> {
        return moviesRepo.loadMovies(params.query, params.page)
    }
}