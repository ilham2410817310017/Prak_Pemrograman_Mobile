package com.example.scrollablelistcompose.feature.movie.domain.usecase

import com.example.scrollablelistcompose.feature.movie.data.local.entity.MovieEntity
import com.example.scrollablelistcompose.feature.movie.domain.repository.MovieRepository
import com.example.scrollablelistcompose.core.network.ApiResult
import kotlinx.coroutines.flow.Flow

class MovieUseCase(private val repository: MovieRepository) {

    fun getMovies(): Flow<List<MovieEntity>> {
        return repository.getMoviesFromLocal()
    }

    suspend fun refreshMovies(): ApiResult<Unit> {
        return repository.refreshMovies()
    }

    fun saveLastViewedMovie(title: String) {
        repository.saveLastViewedMovie(title)
    }

    fun getLastViewedMovie(): String {
        return repository.getLastViewedMovie()
    }
}