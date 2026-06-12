package com.example.scrollablelistcompose.feature.movie.data.mapper

import com.example.scrollablelistcompose.feature.movie.data.local.entity.MovieEntity
import com.example.scrollablelistcompose.feature.movie.data.remote.dto.MovieDto


fun MovieDto.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title ?: "Judul Tidak Diketahui",
        overview = overview ?: "Tidak ada deskripsi.",
        posterPath = posterPath ?: "",
        releaseDate = releaseDate ?: "-",
        voteAverage = voteAverage ?: 0.0
    )
}