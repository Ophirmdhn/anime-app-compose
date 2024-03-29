package com.ophi.animeapp.data

import com.ophi.animeapp.model.Anime
import com.ophi.animeapp.model.DummyDataSource

class AnimeRepository {

    private val anime = mutableListOf<Anime>()

    init {
        if (anime.isEmpty()) {
            DummyDataSource.dummyAnime.forEach {
                anime.add(it)
            }
        }
    }

    fun getAllAnime(): List<Anime> = anime

    fun getAnimeById(id: Int): Anime = anime.first { it.id == id }

    fun setFavorite(id: Int, value: Boolean) {
        val index = anime.indexOfFirst { it.id == id }
        anime[index] = anime[index].copy(isFavorite = value)
    }

    fun getFavorite(): List<Anime> = anime.filter { it.isFavorite }

    companion object {
        @Volatile
        private var instance: AnimeRepository? = null

        fun getInstance(): AnimeRepository =
            instance ?: synchronized(this) {
                AnimeRepository().apply {
                    instance = this
                }
            }
    }
}