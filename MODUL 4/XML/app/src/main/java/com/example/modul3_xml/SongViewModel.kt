package com.example.modul3_xml

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class SongViewModel(private val categoryName: String) : ViewModel() {
    private val _songList = MutableStateFlow<List<Song>>(emptyList())
    val songList: StateFlow<List<Song>> = _songList.asStateFlow()

    private val _navigationEvent = MutableStateFlow<Song?>(null)
    val navigationEvent: StateFlow<Song?> = _navigationEvent.asStateFlow()

    private val _intentEvent = MutableStateFlow<String?>(null)
    val intentEvent: StateFlow<String?> = _intentEvent.asStateFlow()

    init {
        loadSongs()
    }

    private fun loadSongs() {
        Timber.d("[$categoryName] Prosses memasukkan data lagu ke dalam list dimulai...")
        _songList.value = SongData.songs
        Timber.d("Berhasil memuat ${_songList.value.size} data item lagu.")
    }

    fun onDetailClicked(song: Song) {
        Timber.d("Tombol 'Detail' pada UI telah ditekan pengguna.")
        Timber.d("Mempersiapkan data Navigasi -> ID Lagu: ${song.id}, Judul: ${song.title}")
        _navigationEvent.value = song
    }

    fun onIntentClicked(link: String) {
        Timber.d("Tombol 'Listen' ditekan. Mengeksekusi explicit intent ke external url: $link")

        _intentEvent.value = link
    }

    fun onNavigationHandled() { _navigationEvent.value = null }
    fun onIntentHandled() { _intentEvent.value = null }
}

class SongViewModelFactory(private val categoryParam: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SongViewModel(categoryParam) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}