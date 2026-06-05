package com.example.modul5compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.modul5compose.data.model.Movie
import com.example.modul5compose.data.repository.MovieRepository
import com.example.modul5compose.data.repository.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movieState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val movieState: StateFlow<UiState<List<Movie>>> = _movieState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<Movie?>(null)
    val navigationEvent: StateFlow<Movie?> = _navigationEvent.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        Timber.d("Memuat data film populer dari TMDB API...")

        viewModelScope.launch {
            repository.getPopularMovies().collect { state ->
                _movieState.value = state

                when (state) {
                    is UiState.Success -> Timber.d("Berhasil memuat ${state.data.size} item film.")
                    is UiState.Error -> Timber.e("Gagal memuat film: ${state.errorMessage}")
                    is UiState.Loading -> Timber.d("Status masih loading...")
                }
            }
        }
    }

    fun onDetailClicked(movie: Movie) {
        Timber.d("Tombol Detail ditekan. Navigasi ke ID Film: ${movie.id} Judul: ${movie.title}.")
        _navigationEvent.value = movie
    }

    fun onNavigationHandled() {
        _navigationEvent.value = null
    }
}

class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}