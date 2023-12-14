package com.ophi.animeapp.ui.screen.home

import androidx.lifecycle.ViewModel
import com.ophi.animeapp.data.AnimeRepository
import com.ophi.animeapp.model.Anime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(private val repository: AnimeRepository): ViewModel() {

    private val _anime: MutableStateFlow<List<Anime>> = MutableStateFlow(
        repository.getAllAnime()
    )

    val anime = _anime.asStateFlow()
}