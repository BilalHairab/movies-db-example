package com.bilal.movies.app.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bilal.movies.domain.base.DataHolder
import com.bilal.movies.domain.models.Movie
import com.bilal.movies.domain.usecases.GetMoviesParams
import com.bilal.movies.domain.usecases.GetMoviesUseCase
import com.bilal.movies.domain.usecases.MoviesSortTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent

/**
 * Created by Bilal Hairab on 03/02/2022.
 */
class MoviesListViewModel : ViewModel() {
    private val PAGE_SIZE = 20

    private var currentPopularPage = 1
    private var currentRatedPage = 1
    private var currentRevenuePage = 1

    private val getMoviesUseCase: GetMoviesUseCase by KoinJavaComponent.inject(
        GetMoviesUseCase::class.java
    )

    val errorLiveData = MutableLiveData<String>()

    val popularMoviesLiveData = MutableLiveData<ArrayList<Movie>>()
    val topRatedMoviesLiveData = MutableLiveData<ArrayList<Movie>>()
    val revenueMoviesLiveData = MutableLiveData<ArrayList<Movie>>()

    fun getPopularPage() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getNewItemsPage(MoviesSortTypes.POPULARITY_DESC, currentPopularPage)
            withContext(Dispatchers.Main) {
                if (result is DataHolder.Success) {
                    popularMoviesLiveData.value = result.data
                    currentPopularPage++
                } else if (result is DataHolder.Fail) {
                    propagateError(result.error.name)
                }
            }
        }
    }

    fun getTopRatedPage() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getNewItemsPage(MoviesSortTypes.RATED_DESC, currentRatedPage)
            withContext(Dispatchers.Main) {
                if (result is DataHolder.Success) {
                    topRatedMoviesLiveData.value = result.data
                    currentRatedPage++
                } else if (result is DataHolder.Fail) {
                    propagateError(result.error.name)
                }
            }
        }
    }

    fun getRevenuePage() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getNewItemsPage(MoviesSortTypes.REVENUE_DESC, currentRevenuePage)
            withContext(Dispatchers.Main) {
                if (result is DataHolder.Success) {
                    revenueMoviesLiveData.value = result.data
                    currentRevenuePage++
                } else if (result is DataHolder.Fail) {
                    propagateError(result.error.name)
                }
            }
        }
    }

    private suspend fun getNewItemsPage(
        sortType: MoviesSortTypes,
        page: Int
    ): DataHolder<ArrayList<Movie>> {
        return getMoviesUseCase.execute(GetMoviesParams(sortType, PAGE_SIZE, page))
    }

    private fun propagateError(error: String) {
        errorLiveData.value = error
    }
}