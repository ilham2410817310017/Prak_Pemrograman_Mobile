package com.example.scrollablelistcompose.feature.movie.data.repository

import com.example.scrollablelistcompose.core.network.ApiResult
import com.example.scrollablelistcompose.core.network.safeApiCall
import com.example.scrollablelistcompose.core.preferences.SharedPrefsHelper
import com.example.scrollablelistcompose.feature.movie.data.local.dao.MovieDao
import com.example.scrollablelistcompose.feature.movie.data.local.entity.MovieEntity
import com.example.scrollablelistcompose.feature.movie.data.mapper.toEntity
import com.example.scrollablelistcompose.feature.movie.data.remote.api.MovieApi
import com.example.scrollablelistcompose.feature.movie.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val api: MovieApi,
    private val dao: MovieDao,
    private val prefsHelper: SharedPrefsHelper
) : MovieRepository {

    override fun getMoviesFromLocal(): Flow<List<MovieEntity>> {
        return dao.getAllMovies()
    }

    override suspend fun refreshMovies(): ApiResult<Unit> {
        val result = safeApiCall { api.getPopularMovies() }

        return when (result) {
            is ApiResult.Success -> {
                val response = result.data
                if (response.isSuccessful) {
                    val movieDtos = response.body()?.results ?: emptyList()
                    val movieEntities = movieDtos.map { it.toEntity() }

                    withContext(Dispatchers.IO) {
                        dao.deleteAllMovies()
                        dao.insertMovies(movieEntities)
                    }

                    ApiResult.Success(Unit)
                } else {
                    ApiResult.Error(message = "Gagal mengambil data", code = response.code())
                }
            }
            is ApiResult.Error -> {
                ApiResult.Error(message = result.message, code = result.code)
            }
            is ApiResult.Loading -> ApiResult.Loading
        }
    }

    override fun saveLastViewedMovie(title: String) {
        prefsHelper.saveLastViewedMovie(title)
    }

    override fun getLastViewedMovie(): String {
        return prefsHelper.getLastViewedMovie()
    }
}