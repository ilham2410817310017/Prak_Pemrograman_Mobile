package com.ilham.scrollablelistapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilham.scrollablelistapp.R
import com.ilham.scrollablelistapp.model.Movie

class MovieViewModel : ViewModel() {
    // LiveData untuk menyimpan list film agar aman dari rotasi
    private val _movieList = MutableLiveData<ArrayList<Movie>>()
    val movieList: LiveData<ArrayList<Movie>> = _movieList

    init {
        loadMovieData()
    }

    private fun loadMovieData() {
        val list = ArrayList<Movie>()

        // Movie 1: Avengers Endgame
        list.add(Movie(1, "Avengers: Endgame", "2019", "Para Avengers yang tersisa bersatu kembali untuk memulihkan kehancuran alam semesta.", R.drawable.movie1, "https://www.imdb.com/title/tt4154796/"))

        // Movie 2: Avengers Infinity War
        list.add(Movie(2, "Avengers: Infinity War", "2018", "Iron Man, Thor, dan Hulk bekerja sama untuk mencegah Thanos memusnahkan separuh semesta.", R.drawable.movie2, "https://www.imdb.com/title/tt4154756/"))

        // Movie 3: The Avengers
        list.add(Movie(3, "The Avengers", "2012", "Pahlawan terkuat di Bumi harus belajar bertarung sebagai tim untuk menghentikan Loki.", R.drawable.movie3, "https://www.imdb.com/title/tt0848228/"))

        // Movie 4: Shang-Chi
        list.add(Movie(4, "Shang-Chi and the Legend of the Ten Rings", "2021", "Shang-Chi harus menghadapi masa lalunya saat ia ditarik ke dalam organisasi Ten Rings.", R.drawable.movie4, "https://www.imdb.com/title/tt9376612/"))

        // Movie 5: Spider-Man Brand New Day
        list.add(Movie(5, "Spider-Man: Brand New Day", "2024", "Era baru bagi Peter Parker dengan tantangan dan musuh yang belum pernah ia temui sebelumnya.", R.drawable.movie5, "https://www.imdb.com/title/tt16373194/"))

        _movieList.value = list
    }
}