package com.ophi.animeapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ophi.animeapp.R
import com.ophi.animeapp.ui.theme.AnimeAppTheme


@Composable
fun AnimeCard(
    image: Int,
    title: String,
    genre:List<String>,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f / 6f),
        )
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(
                start = 8.dp,
                top = 8.dp
            )
        )
        Text(
            text = genre.joinToString(", "),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic,
            color = Color.Black,
            modifier = Modifier.padding(
                start = 8.dp,
                bottom = 8.dp
            )
        )
    }
}


@Composable
@Preview(showBackground = true)
fun CardPreview() {
    AnimeAppTheme {
        AnimeCard(image = R.drawable.one_piece, title = "One Piece", genre = listOf("Action", "Adventure", "Comedy"))
    }
}