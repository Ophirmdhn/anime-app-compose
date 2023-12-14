package com.ophi.animeapp.di

import com.ophi.animeapp.data.AnimeRepository

object Injection {
    fun provideRepository(): AnimeRepository {
        return AnimeRepository.getInstance()
    }
}