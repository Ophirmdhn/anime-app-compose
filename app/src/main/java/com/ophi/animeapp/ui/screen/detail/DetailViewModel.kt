package com.ophi.animeapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import com.ophi.animeapp.data.AnimeRepository
import com.ophi.animeapp.model.Anime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel(private val repository: AnimeRepository): ViewModel() {
    private val _anime: MutableStateFlow<Anime?> = MutableStateFlow(null)
    val anime = _anime.asStateFlow()

    fun getAnimeById(id: Int) {
        _anime.value = repository.getAnimeById(id)
    }
}