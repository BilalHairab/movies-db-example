package com.bilal.movies.data.datasources.movies.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bilal.movies.data.custom.MovieInDB

/**
 * Created by Bilal Hairab on 02/02/2022.
 */
@Database(entities = [MovieInDB::class], version = 1)
@TypeConverters(MovieDataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesLocalDao

    companion object {

        private val DATABASE_NAME: String = "Movies"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}