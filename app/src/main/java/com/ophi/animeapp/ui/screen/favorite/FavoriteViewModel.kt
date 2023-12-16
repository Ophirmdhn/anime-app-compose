package com.ophi.animeapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import com.ophi.animeapp.data.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoriteViewModel(private val repository: AnimeRepository): ViewModel() {
    private val _favorite = MutableStateFlow(
        repository.getFavorite()
    )

    val favorite = _favorite.asStateFlow()

    fun setFavorite(id: Int, value: Boolean) {
        repository.setFavorite(id, value)
        _favorite.value = repository.getFavorite()
    }

    fun getFavorite() {
        _favorite.value = repository.getFavorite()
    }
}