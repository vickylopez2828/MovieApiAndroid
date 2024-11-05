package ar.edu.unicen.seminario.ui.movies

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import ar.edu.unicen.seminario.ddl.models.Movie

@Composable
fun MovieContent(
    movies: LazyPagingItems<Movie>,
    onMovieClicked: (id: Int) -> Unit
){
    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            contentAlignment = Alignment.Center
    )
    {
        //similar a recyclerview
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
        ) {
            items(movies.itemCount) {
                movies[it]?.let { movie ->
                    MovieItem(
                        title = movie.title,
                        image = movie.image,
                        similarMovies = false,
                        onClick = {
                            onMovieClicked(movie.id)
                        }
                    )
                }
            }
        }
    }
}
