package com.ilham.scrollablelistapp.viewmodel

import androidx.lifecycle.ViewModel
import com.ilham.scrollablelistapp.R
import com.ilham.scrollablelistapp.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class MovieViewModel(val userTag: String) : ViewModel() {
    private val _movieList = MutableStateFlow<List<Movie>>(emptyList())
    val movieList: StateFlow<List<Movie>> = _movieList
    private val _navigateToDetail = MutableStateFlow<Movie?>(null)
    val navigateToDetail: StateFlow<Movie?> = _navigateToDetail

    init {
        loadMovieData()
    }

    private fun loadMovieData() {
        val list = ArrayList<Movie>()

        list.add(Movie(1, "Avengers: Endgame", "2019", "Para Avengers yang tersisa bersatu kembali untuk memulihkan kehancuran alam semesta.", R.drawable.movie1, "https://www.imdb.com/title/tt4154796/"))
        list.add(Movie(2, "Avengers: Infinity War", "2018", "Iron Man, Thor, dan Hulk bekerja sama untuk mencegah Thanos memusnahkan separuh semesta.", R.drawable.movie2, "https://www.imdb.com/title/tt4154756/"))
        list.add(Movie(3, "The Avengers", "2012", "Pahlawan terkuat di Bumi harus belajar bertarung sebagai tim untuk menghentikan Loki.", R.drawable.movie3, "https://www.imdb.com/title/tt0848228/"))
        list.add(Movie(4, "Shang-Chi and the Legend of the Ten Rings", "2021", "Shang-Chi harus menghadapi masa lalunya saat ia ditarik ke dalam organisasi Ten Rings.", R.drawable.movie4, "https://www.imdb.com/title/tt9376612/"))
        list.add(Movie(5, "Spider-Man: Brand New Day", "2024", "Era baru bagi Peter Parker dengan tantangan dan musuh yang belum pernah ia temui sebelumnya.", R.drawable.movie5, "https://www.imdb.com/title/tt16373194/"))

        _movieList.value = list

        Timber.d("Data film berhasil dimuat oleh: $userTag. Jumlah data: ${list.size}")
    }

    fun onMovieClicked(movie: Movie) {
        _navigateToDetail.value = movie

        Timber.i("Tombol Detail ditekan untuk film: ${movie.title}")
    }

    fun onNavigated() {
        _navigateToDetail.value = null
    }
}