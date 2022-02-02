package com.bilal.movies.app.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bilal.movies.domain.models.Movie

/**
 * Created by Bilal Hairab on 03/02/2022.
 */
class SharedSelectedMovieViewModel : ViewModel() {
    val selectedMovieLiveData = MutableLiveData<Movie>()

    fun select(movie: Movie) {
        selectedMovieLiveData.value = movie
    }
}