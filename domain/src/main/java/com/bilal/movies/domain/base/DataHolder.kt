package com.bilal.movies.domain.base

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
sealed class DataHolder<out T : Any> {
    data class Success<out T : Any>(val data: T) : DataHolder<T>()

    data class Fail(val error: ResultError) : DataHolder<Nothing>()
}

enum class ResultError {
    //Database Error
    DATA_NOT_FOUND,

    //Server Error
    UNKNOWN_ERROR_WITH_SERVER
}