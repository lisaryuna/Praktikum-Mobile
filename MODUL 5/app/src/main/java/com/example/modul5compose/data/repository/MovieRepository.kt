package com.example.modul5compose.data.repository

import com.example.modul5compose.BuildConfig
import com.example.modul5compose.data.local.MovieDao
import com.example.modul5compose.data.model.Movie
import com.example.modul5compose.data.network.ApiConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

sealed class UiState<out T : Any?> {
    object Loading : UiState<Nothing>()
    data class Success<out T : Any>(val data: T) : UiState<T>()
    data class Error(val errorMessage: String) : UiState<Nothing>()
}

class MovieRepository(private val movieDao: MovieDao) {
    private val apiService = ApiConfig.getApiService()

    private val apiKey = BuildConfig.TMDB_API_KEY

    fun getPopularMovies(language: String) : Flow<UiState<List<Movie>>> = flow {
        emit(UiState.Loading)

        try {
            val response = apiService.getPopularMovies(apiKey = apiKey, language = language)
            val moviesFromApi = response.results

            movieDao.deleteAllMovies()
            movieDao.insertMovies(moviesFromApi)
            Timber.d("Data berhasil diambil dari API dan disimpan ke database lokal.")
        } catch (e: Exception) {
            Timber.e("Gagal mengambil data dari API, mencoba menggunakan data lokal. Error: ${e.message}")
        }

        try {
            movieDao.getAllMovies().collect { localMovies ->
                if (localMovies.isNotEmpty()) {
                    emit(UiState.Success(localMovies))
                } else {
                    emit(UiState.Error("Tidak ada koneksi internet dan belum ada data yang tersimpan."))
                }
            }
        } catch (e: Exception) {
            emit(UiState.Error("Gagal membaca database: ${e.message}"))
        }
    }
}