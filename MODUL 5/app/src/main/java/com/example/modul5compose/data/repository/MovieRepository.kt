package com.example.modul5compose.data.repository

import com.example.modul5compose.BuildConfig
import com.example.modul5compose.data.model.Movie
import com.example.modul5compose.data.network.ApiConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class UiState<out T : Any?> {
    object Loading : UiState<Nothing>()
    data class Success<out T : Any>(val data: T) : UiState<T>()
    data class Error(val errorMessage: String) : UiState<Nothing>()
}

class MovieRepository {
    private val apiService = ApiConfig.getApiService()

    private val apiKey = BuildConfig.TMDB_API_KEY

    fun getPopularMovies(language: String) : Flow<UiState<List<Movie>>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.getPopularMovies(apiKey = apiKey, language = language)
            emit(UiState.Success(response.results))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "Terjadi kesalahan jaringan"))
        }
    }
}