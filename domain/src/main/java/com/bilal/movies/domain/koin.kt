package com.bilal.movies.domain

import com.bilal.movies.domain.usecases.GetMoviesUseCase
import com.bilal.movies.domain.usecases.LoadImageUseCase
import org.koin.dsl.module

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
val domainModule = module {
    factory {
        GetMoviesUseCase(
            moviesRepo = get()
        )
    }
    factory {
        LoadImageUseCase(
            imagesRepo = get()
        )
    }
}