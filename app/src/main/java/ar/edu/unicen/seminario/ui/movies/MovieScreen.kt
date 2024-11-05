package ar.edu.unicen.seminario.ui.movies

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import ar.edu.unicen.seminario.ui.MainViewModel
import ar.edu.unicen.seminario.ui.MovieTrendingViewModel

//se comunica con view model y redibuja el contenido
@Composable
fun MovieScreen(
    viewModel: MainViewModel,
    trendingViewModel: MovieTrendingViewModel,
    goDetail: (id: Int) -> Unit
){
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val hasNetworkError by viewModel.hasNetworkError.collectAsState()

    LaunchedEffect(selectedTabIndex) {
        if(selectedTabIndex == 0 ){
            viewModel.getMovies()
        }
        else if (selectedTabIndex == 1) {
            trendingViewModel.getTrendingMovies()
        }
    }
    //si cambia en el view model aca lo esta escuchando y redibuja
    //suscripción al flujo de datos de paginación
    val movies = viewModel.movies.collectAsLazyPagingItems()
    val trendingMovies =  trendingViewModel.trendingMovies.collectAsLazyPagingItems()

    var isClicked by remember { mutableStateOf(false) }
    //remember para q recuerde valor, el state para q este escuhando
    //algo similar podria ser con un texto q se va escribiendo
//    var counter by remember { mutableIntStateOf(0) }
    //titulos de las tabs
    val tabs = listOf("Populares", "Tendencias")

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(
                            title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                            ) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = when (selectedTabIndex) {
                    0 -> tabs[selectedTabIndex]
                    1 -> tabs[selectedTabIndex]
                    else -> ""
                },
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
            )
            if (hasNetworkError) {

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay conexión a internet. Por favor, verifica tu conexión.", color = Color.Red)
                }
            } else {
                when {
                    //carga inicial
                    movies.loadState.refresh is LoadState.Loading && movies.itemCount == 0 -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(64.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    //carga inicial
                    trendingMovies.loadState.refresh is LoadState.Loading && trendingMovies.itemCount == 0 -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(64.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    //esatdo error
                    movies.loadState.hasError -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    viewModel.getMovies()
                                    isClicked = !isClicked
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isClicked) MaterialTheme.colorScheme.primary else Color.DarkGray
                                )
                            ) {
                                Text("Reintentar")
                            }
                        }
                        Toast.makeText(
                            LocalContext.current,
                            "Error al cargar las películas",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    //estado error para trendingmovies
                    trendingMovies.loadState.hasError -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    trendingViewModel.getTrendingMovies()
                                    isClicked = !isClicked
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isClicked) MaterialTheme.colorScheme.primary else Color.DarkGray
                                )
                            ) {
                                Text("Reintentar")
                            }
                        }
                        Toast.makeText(
                            LocalContext.current,
                            "Error al cargar las películas",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    else -> {
                        when (selectedTabIndex) {
                            0 -> MovieContent(
                                movies = movies,
                                onMovieClicked = goDetail
                            )

                            1 -> MovieContent(
                                movies = trendingMovies,
                                onMovieClicked = goDetail
                            )
                        }
                        if (movies.loadState.append is LoadState.Loading || trendingMovies.loadState.append is LoadState.Loading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(64.dp),
                                    color = MaterialTheme.colorScheme.onError
                                )
                            }
                        }
                    }
                }
            }

        }
    }


}
