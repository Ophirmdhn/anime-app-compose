package com.ophi.animeapp.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ophi.animeapp.di.Injection
import com.ophi.animeapp.model.Anime
import com.ophi.animeapp.ui.ViewModelFactory
import com.ophi.animeapp.ui.component.AnimeCard
import com.ophi.animeapp.ui.theme.AnimeAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val anime by viewModel.anime.collectAsState()
    
    HomeContent(
        anime = anime,
        modifier = modifier
    )
}

@Composable
fun HomeContent(
    anime: List<Anime>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(anime) { data ->
            AnimeCard(
                image = data.image,
                title = data.title,
                genre = data.genre
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    AnimeAppTheme {
        HomeScreen()
    }
}