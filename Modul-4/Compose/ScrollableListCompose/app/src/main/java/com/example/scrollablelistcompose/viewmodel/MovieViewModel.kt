package com.example.scrollablelistcompose.viewmodel

import androidx.lifecycle.ViewModel
import com.example.scrollablelistcompose.model.Movie
import com.example.scrollablelistcompose.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class MovieViewModel(private val userName: String) : ViewModel() {

    // Menggunakan StateFlow sesuai standar Modul 4
    private val _movieList = MutableStateFlow<List<Movie>>(emptyList())
    val movieList: StateFlow<List<Movie>> = _movieList.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        _movieList.value = listOf(
            Movie(1, "Avengers: Endgame", "2019", "Para Avengers yang tersisa bersatu kembali untuk memulihkan kehancuran alam semesta.", R.drawable.movie1, "https://www.imdb.com/title/tt4154796/"),
            Movie(2, "Avengers: Infinity War", "2018", "Iron Man, Thor, dan Hulk bekerja sama untuk mencegah Thanos memusnahkan separuh semesta.", R.drawable.movie2, "https://www.imdb.com/title/tt4154756/"),
            Movie(3, "The Avengers", "2012", "Pahlawan terkuat di Bumi harus belajar bertarung sebagai tim untuk menghentikan Loki.", R.drawable.movie3, "https://www.imdb.com/title/tt0848228/"),
            Movie(4, "Shang-Chi and the Legend of the Ten Rings", "2021", "Shang-Chi harus menghadapi masa lalunya saat ia ditarik ke dalam organisasi Ten Rings.", R.drawable.movie4, "https://www.imdb.com/title/tt9376612/"),
            Movie(5, "Spider-Man: Brand New Day", "2024", "Era baru bagi Peter Parker dengan tantangan dan musuh yang belum pernah ia temui sebelumnya.", R.drawable.movie5, "https://www.imdb.com/title/tt16373194/")
        )

        // Log Timber saat data berhasil dimuat
        Timber.d("Data film berhasil dimuat untuk user: $userName")
    }

    fun onMovieClicked(movie: Movie) {
        Timber.d("Item diklik: ${movie.title}")
    }
}