package ar.edu.unicen.seminario.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminario.ddl.data.MovieRepository
import ar.edu.unicen.seminario.ddl.models.Movie
import ar.edu.unicen.seminario.ddl.models.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieSimilarViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel(){
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()

    private val _similarMovies = MutableStateFlow<List<Movie>?>(null)
    val similarMovies = _similarMovies.asStateFlow()
    fun getSimilarMovies(
        id: Int
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = false

            delay(500)
            val similarMovies = movieRepository.getSimilarMovies(id)

            _loading.value = false
            _similarMovies.value = similarMovies
            _error.value = similarMovies == null
        }
    }
    fun clearSimilarMovies() {
        _similarMovies.value = emptyList()
    }

}