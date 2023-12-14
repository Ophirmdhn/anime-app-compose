package com.ophi.animeapp.model

data class Anime(
    val id: Int,
    val title: String,
    val synopsis: String,
    val genre: List<String>,
    val release: String,
    val image: Int,
    val isFavorite: Boolean = false
)
