package com.example.scrollablelistcompose.model

import java.io.Serializable

data class Movie(
    val id: Int,
    val title: String,
    val year: String,
    val plot: String,
    val imageRes: Int,
    val imdbUrl: String
)