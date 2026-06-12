package com.example.scrollablelistcompose.feature.movie.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.scrollablelistcompose.feature.movie.domain.usecase.MovieUseCase

class MovieViewModelFactory(
    private val name: String,
    private val useCase: MovieUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(name, useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}