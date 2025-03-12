package eu.anifantakis.mitropolitikomoviesadvanced.movies.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.anifantakis.mitropolitikomoviesadvanced.movies.domain.Movie
import java.util.Calendar

@Composable
fun RowItem(movie: Movie, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.
        padding(vertical = 4.dp)
    ) {
        Row {
            ThumbnailLoader(
                imagePath = movie.posterPath
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Top)
                    .fillMaxSize()
                    .heightIn(120.dp)
                    .padding(8.dp)
                ,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,

                    )

                Text(
                    text = Calendar.getInstance().apply {
                        time = movie.releaseDate
                    }.get(Calendar.YEAR).toString())

            }
        }
    }
}