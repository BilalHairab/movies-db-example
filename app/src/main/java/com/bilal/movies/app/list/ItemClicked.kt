package com.bilal.movies.app.list

import android.view.View
import com.bilal.movies.domain.models.Movie

/**
 * Created by Bilal Hairab on 03/02/2022.
 */
interface ItemClicked {
    fun onItemClicked(view: View, movie: Movie)
}