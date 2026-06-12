package com.example.scrollablelistcompose.feature.movie.domain.repository

import com.example.scrollablelistcompose.feature.movie.data.local.entity.MovieEntity
import com.example.scrollablelistcompose.core.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMoviesFromLocal(): Flow<List<MovieEntity>>
    suspend fun refreshMovies(): ApiResult<Unit>
    fun saveLastViewedMovie(title: String)
    fun getLastViewedMovie(): String
}