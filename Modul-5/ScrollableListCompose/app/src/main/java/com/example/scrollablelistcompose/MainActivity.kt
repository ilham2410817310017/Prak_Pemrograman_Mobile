package com.example.scrollablelistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.scrollablelistcompose.feature.movie.data.local.AppDatabase
import com.example.scrollablelistcompose.feature.movie.data.remote.api.MovieApi
import com.example.scrollablelistcompose.feature.movie.presentation.screens.DetailScreen
import com.example.scrollablelistcompose.feature.movie.presentation.screens.MainScreen
import com.example.scrollablelistcompose.feature.movie.presentation.viewModel.MovieViewModel
import com.example.scrollablelistcompose.feature.movie.presentation.viewModel.MovieViewModelFactory
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            com.example.scrollablelistcompose.feature.movie.data.local.AppDatabase::class.java,
            com.example.scrollablelistcompose.feature.movie.data.local.AppDatabase.DATABASE_NAME
        ).build()

        val api = com.example.scrollablelistcompose.core.network.ApiClient.retrofit.create(
            com.example.scrollablelistcompose.feature.movie.data.remote.api.MovieApi::class.java
        )

        val prefsHelper = com.example.scrollablelistcompose.core.preferences.SharedPrefsHelper(applicationContext)

        val repository = com.example.scrollablelistcompose.feature.movie.data.repository.MovieRepositoryImpl(
            api = api,
            dao = database.movieDao,
            prefsHelper = prefsHelper
        )

        val useCase = com.example.scrollablelistcompose.feature.movie.domain.usecase.MovieUseCase(repository)

        setContent {
            val navController = rememberNavController()
            val viewModel: MovieViewModel = viewModel(
                factory = MovieViewModelFactory("Muhammad Ilham", useCase)
            )

            Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF1A1A1A)) {
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(
                            viewModel = viewModel,
                            onDetailClick = { movieTitle ->
                                navController.navigate("detail/$movieTitle")
                            }
                        )
                    }
                    composable("detail/{movieTitle}") { backStackEntry ->
                        val title = backStackEntry.arguments?.getString("movieTitle")
                        DetailScreen(
                            title = title ?: "",
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}