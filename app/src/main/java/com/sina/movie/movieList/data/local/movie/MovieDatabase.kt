package com.sina.movie.movieList.data.local.movie

import androidx.room.Dao
import androidx.room.Database

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase {

    abstract val movieDao: MovieDao
}