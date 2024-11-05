package ar.edu.unicen.seminario.ui.movie

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.unicen.seminario.R
import ar.edu.unicen.seminario.common.Constants
import ar.edu.unicen.seminario.ui.MovieSimilarViewModel
import ar.edu.unicen.seminario.ui.MovieViewModel
import ar.edu.unicen.seminario.ui.movies.MovieItem
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import quicksandFamily


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    similarViewModel: MovieSimilarViewModel,
    viewModel: MovieViewModel,
    navController: NavController,
) {
    //cuando id cambia se ejecuta, vuelve a pedir una peli
    LaunchedEffect(movieId) {
        viewModel.getMovie(movieId)
        similarViewModel.clearSimilarMovies()
        similarViewModel.getSimilarMovies(movieId)
    }
    //stateflow es un flujo d edatos q mantiene ele stado mas reciente y emite actualizaciones cada vez q cambia
    //collect  convierte un flujo en un estado observable dentro de los Composables
    //movie se actualizara autom cada vez q el flujo emita un nuevo valor
    val movie by viewModel.movie.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val hasError by viewModel.error.collectAsState()

    val errorSimilarMovies by similarViewModel.error.collectAsState()
    val isLoadingSimilarMovies by similarViewModel.loading.collectAsState()
    val similarMovies by similarViewModel.similarMovies.collectAsState()
    val hasNetworkError by viewModel.hasNetworkError.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Icono",
                tint = Color.DarkGray,
                modifier = Modifier.clickable { navController.popBackStack() },
            )
            Button(
                modifier = Modifier
                    .width(150.dp),
                onClick = { navController.navigate("movies") }
            ) {
                Text(
                    text = "Menu Principal"
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else if (hasNetworkError){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay conexión a internet. Por favor, verifica tu conexión.", color = Color.Red)
            }
        }
        else if (hasError) {
            Toast.makeText(LocalContext.current, "Error al cargar la película", Toast.LENGTH_LONG).show()
            Text(
                text = "Error al cargar la película",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.getMovie(movieId) },
            ) {
                Text("Reintentar")
            }
        } else {
            movie?.let {
                var imageUrl = movie?.imageDetail
                if(movie?.imageDetail == null){
                    GlideImage(
                        modifier = Modifier.defaultMinSize(150.dp),
                        model = R.drawable.image,
                        contentDescription = null
                    )
                } else {
                    imageUrl = Constants.IMAGE_DETAIL_URL + imageUrl
                    GlideImage(
                        model = imageUrl,
                        contentDescription = null
                    )
                }
                Text(
                    text = it.title,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Black
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(32.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Descripción",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Black
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 6.dp),
                        text = it.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 6.dp),
                        text = "Puntuación: ${it.rating}",
                        style = TextStyle(
                            fontFamily = quicksandFamily,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = colorResource(id = R.color.red)
                    )
                    Row {
                        Text(
                            text = "Géneros: ",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            itemsIndexed(it.genres) { index, genre ->
                                if (index != it.genres.lastIndex) {
                                    Text(
                                        text = "${genre},",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                } else {
                                    Text(
                                        text = genre,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        val moviesToDisplay = similarMovies ?: emptyList()
        if(!hasError){
            if (errorSimilarMovies) {
                Toast.makeText(LocalContext.current,"Error al cargar lista de peliculas similares", Toast.LENGTH_LONG).show()
            } else if (isLoadingSimilarMovies && !isLoading){
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            }
            else  {
                if (moviesToDisplay.isNotEmpty()) {
                    Box (
                        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                        contentAlignment = Alignment.TopStart
                    ){
                        Text(
                            text = "Películas Similares",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                        Spacer(modifier = Modifier.height(16.dp))
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            items(moviesToDisplay) { movie ->
                              //  val img = movie.image ?: R.drawable.image
                                MovieItem(
                                    title = movie.title,
                                    image = movie.image,
                                    similarMovies = true,
                                    onClick = { navController.navigate("movieDetail/${movie.id}") }
                                )
                                //
                            }
                        }
                }
            }
        }
    }

}


//@Composable
//@Preview(showBackground = true)
//fun UserScreenPreview(){
//    MovieDetailScreen(
//        title = "Vicky Lopez",
//        image = "",
//        description = "",
//        rating = 4.5.toFloat(),
//        navController = NavController()
//    )
//}