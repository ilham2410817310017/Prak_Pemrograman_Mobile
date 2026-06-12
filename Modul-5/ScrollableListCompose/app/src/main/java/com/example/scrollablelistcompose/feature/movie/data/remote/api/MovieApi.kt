package com.example.scrollablelistcompose.feature.movie.data.remote.api

import com.example.scrollablelistcompose.feature.movie.data.remote.dto.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): Response<MovieResponseDto>
}