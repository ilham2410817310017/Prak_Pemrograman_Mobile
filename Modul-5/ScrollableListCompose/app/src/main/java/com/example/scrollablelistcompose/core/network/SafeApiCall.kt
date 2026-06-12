package com.example.scrollablelistcompose.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResult.Success(apiCall())
        } catch (e: HttpException) {
            ApiResult.Error(message = e.localizedMessage ?: "Http Error Occurred", code = e.code())
        } catch (e: IOException) {
            ApiResult.Error(message = "Please check your internet connection")
        } catch (e: Exception) {
            ApiResult.Error(message = e.localizedMessage ?: "Unknown Error")
        }
    }
}