package com.example.scrollablelistcompose.feature.movie.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrollablelistcompose.feature.movie.data.local.entity.MovieEntity
import com.example.scrollablelistcompose.feature.movie.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import com.example.scrollablelistcompose.core.network.ApiResult

class MovieViewModel(
    private val userName: String,
    private val useCase: MovieUseCase
) : ViewModel() {

    private val _movieList = MutableStateFlow<List<MovieEntity>>(emptyList())
    val movieList: StateFlow<List<MovieEntity>> = _movieList.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            launch {
                useCase.getMovies().collect { movies ->
                    _movieList.value = movies
                    if (movies.isNotEmpty()) {
                        Timber.Forest.d("Data film dimuat dari database untuk $userName. Total: ${movies.size} film.")
                    }
                }
            }

            when (val result = useCase.refreshMovies()) {
                is ApiResult.Success -> {
                    Timber.Forest.d("Berhasil request data terbaru dari API TMDB.")
                }
                is ApiResult.Error -> {
                    Timber.Forest.e("Gagal mengambil data dari API: ${result.message}")
                }
                is ApiResult.Loading -> {
                    Timber.Forest.d("Sedang mengambil data...")
                }
            }
        }
    }
    fun saveLastViewedMovie(title: String) {
        useCase.saveLastViewedMovie(title)
    }

    fun getLastViewedMovie(): String {
        return useCase.getLastViewedMovie()
    }
}