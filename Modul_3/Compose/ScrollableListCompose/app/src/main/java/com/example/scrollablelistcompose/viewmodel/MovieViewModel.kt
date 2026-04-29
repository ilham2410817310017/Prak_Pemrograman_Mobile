package com.example.scrollablelistcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.scrollablelistcompose.model.Movie
import com.example.scrollablelistcompose.R

class MovieViewModel : ViewModel() {
    private val _movies = mutableStateOf<List<Movie>>(emptyList())
    val movies: State<List<Movie>> = _movies

    init {
        _movies.value = listOf(
            Movie(1, "Avengers: Endgame", "2019", "Para Avengers yang tersisa bersatu kembali untuk memulihkan kehancuran alam semesta.", R.drawable.movie1, "https://www.imdb.com/title/tt4154796/"),
            Movie(2, "Avengers: Infinity War", "2018", "Iron Man, Thor, dan Hulk bekerja sama untuk mencegah Thanos memusnahkan separuh semesta.", R.drawable.movie2, "https://www.imdb.com/title/tt4154756/"),
            Movie(3, "The Avengers", "2012", "Pahlawan terkuat di Bumi harus belajar bertarung sebagai tim untuk menghentikan Loki.", R.drawable.movie3, "https://www.imdb.com/title/tt0848228/"),
            Movie(4, "Shang-Chi and the Legend of the Ten Rings", "2021", "Shang-Chi harus menghadapi masa lalunya saat ia ditarik ke dalam organisasi Ten Rings.", R.drawable.movie4, "https://www.imdb.com/title/tt9376612/"),
            Movie(5, "Spider-Man: Brand New Day", "2024", "Era baru bagi Peter Parker dengan tantangan dan musuh yang belum pernah ia temui sebelumnya.", R.drawable.movie5, "https://www.imdb.com/title/tt16373194/")
        )
    }
}