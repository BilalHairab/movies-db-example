package com.bilal.movies.domain.base

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
abstract class BaseUseCase<in Params>(private val repository: BaseRepository) {

    abstract suspend fun execute(params: Params): DataHolder<Any>
}