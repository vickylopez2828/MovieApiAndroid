package ar.edu.unicen.seminario.ui

import MyAppTheme
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import ar.edu.unicen.seminario.ui.movie.MovieDetailScreen
import ar.edu.unicen.seminario.ui.movies.MovieScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val trendingViewModel by viewModels<MovieTrendingViewModel>()
    private val movieViewModel by viewModels<MovieViewModel>()
    private val similarMovieViewModel by  viewModels<MovieSimilarViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyAppTheme{
                //controla la navegacion
                val navController = rememberNavController()
                //contiene las vistasa y las va reemplazando
                NavHost(
                    navController = navController,
                    startDestination = "movies", //por donde arrancara
                ) {
                    composable(
                        route = "movies"
                    ) {
                        //contenido q se muestra cuando la ruta es movies
                        MovieScreen(
                            viewModel = viewModel,
                            trendingViewModel = trendingViewModel,
                            goDetail = { id ->
                                navController.navigate(
                                    "movieDetail/${id}"
                                )
                            }
                        )
                    }
                    // composable(movies/{userId})
                    composable(
                        route = "movieDetail/{movieId}", // Definir que esta pantalla recibe el id de la película
                        arguments = listOf(
                            navArgument("movieId") { type = NavType.IntType },
                        ),
                    ) {
                            navBackStackEntry -> //reconstruye la ruta MovieRoute q le pasaron
                            val movieId = navBackStackEntry.arguments?.getInt("movieId") ?: -1

                        MovieDetailScreen(
                            movieId = movieId,
                            similarViewModel = similarMovieViewModel,
                            viewModel = movieViewModel,
                            navController = navController
                        )
                    }

                }
            }

        }

    }

}
