package ar.edu.unicen.seminario.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminario.ddl.data.MovieRepository
import ar.edu.unicen.seminario.ddl.models.Movie
import ar.edu.unicen.seminario.ddl.models.MovieDetail
import ar.edu.unicen.seminario.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val movieRepository: MovieRepository
): ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()

    private val _movie = MutableStateFlow<MovieDetail?>(null)
    val movie = _movie.asStateFlow()
    private val _hasNetworkError = MutableStateFlow(false)
    val hasNetworkError: StateFlow<Boolean> = _hasNetworkError.asStateFlow()

    fun getMovie(
        id: Int
    ) {
        viewModelScope.launch {
            if (!NetworkUtils.isInternetAvailable(context)) {
                _hasNetworkError.value = true
                return@launch
            }

            _hasNetworkError.value = false
            _loading.value = true
            _error.value = false
            _movie.value = null

            val movie = movieRepository.getMovie(id)

            _loading.value = false
            _movie.value = movie
            _error.value = movie == null
        }
    }





}