package com.example.scrollablelistcompose.feature.movie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.scrollablelistcompose.feature.movie.data.local.dao.MovieDao
import com.example.scrollablelistcompose.feature.movie.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {
        const val DATABASE_NAME = "movie_db"
    }
}