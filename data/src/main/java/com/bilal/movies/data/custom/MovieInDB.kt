package com.bilal.movies.data.custom

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bilal.movies.domain.models.Movie

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
@Entity(tableName = "movies")
class MovieInDB(
    val category: String,
    val movie: Movie,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}