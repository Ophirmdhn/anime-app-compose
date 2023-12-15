package com.ophi.animeapp.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ophi.animeapp.R
import com.ophi.animeapp.ui.theme.AnimeAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ophi.animeapp.di.Injection
import com.ophi.animeapp.ui.ViewModelFactory

@Composable
fun DetailScreen(
    animeId: Int,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit
) {
    val anime by viewModel.anime.collectAsState()

    anime?.let {
        DetailContent(
            image = it.image,
            title = it.title,
            synopsis = it.synopsis,
            genre = it.genre,
            release = it.release,
            onBackClick = navigateBack
        )
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    synopsis: String,
    genre: List<String>,
    release: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(image),
                    contentDescription = stringResource(R.string.image_detail),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(550.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.arrow_back),
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp, 8.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = synopsis,
                    textAlign = TextAlign.Justify
                )
            }
            Row{
                Text(
                    text = "Genre : ",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = genre.joinToString(", "),
                    maxLines = 2
                )
            }
            Row{
                Text(
                    text = "Release : ",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = release,
                    textAlign = TextAlign.Left,
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    AnimeAppTheme {
        DetailContent(
            image = R.drawable.one_piece,
            title = "One Piece",
            synopsis = stringResource(R.string.lorem_ipsum),
            genre = listOf("anuh", "inih"),
            release = "1999",
            onBackClick = {}
        )
    }
}