package ar.edu.unicen.seminario.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import ar.edu.unicen.seminario.ddl.data.MovieRepository
import ar.edu.unicen.seminario.ddl.models.Movie
import ar.edu.unicen.seminario.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val movieRepository: MovieRepository

): ViewModel() {

    private val _movies: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(PagingData.empty())
    val movies: StateFlow<PagingData<Movie>> = _movies.asStateFlow()
    private val _hasNetworkError = MutableStateFlow(false)
    val hasNetworkError: StateFlow<Boolean> = _hasNetworkError.asStateFlow()

    fun getMovies(
    ) {
        viewModelScope.launch {
            if (!NetworkUtils.isInternetAvailable(context)) {
                _hasNetworkError.value = true
                return@launch
            }

            _hasNetworkError.value = false
            movieRepository.getMovies().collectLatest { movies ->
                _movies.value = movies
            }
        }
    }



}
