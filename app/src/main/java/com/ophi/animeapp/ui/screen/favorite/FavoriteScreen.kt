package com.ophi.animeapp.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ophi.animeapp.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ophi.animeapp.di.Injection
import com.ophi.animeapp.model.Anime
import com.ophi.animeapp.ui.ViewModelFactory
import com.ophi.animeapp.ui.component.AnimeCard

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (id: Int) -> Unit
) {
    val anime by viewModel.favorite.collectAsState()

    FavoriteContent(
        anime = anime,
        modifier = modifier,
        navigateToDetail = navigateToDetail,
        addToFavorite = viewModel::setFavorite
    )
}

@Composable
fun FavoriteContent(
    anime: List<Anime>,
    modifier: Modifier = Modifier,
    navigateToDetail: (id: Int) -> Unit,
    addToFavorite: (id: Int, value: Boolean) -> Unit
) {
    if (anime.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.don_t_have_favorite),
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier,

            ) {
            items(anime) { data ->
                AnimeCard(
                    image = data.image,
                    title = data.title,
                    genre = data.genre,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.id)
                    }
                )
            }
        }
    }
}