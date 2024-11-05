package ar.edu.unicen.seminario.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import ar.edu.unicen.seminario.ddl.data.MovieRepository
import ar.edu.unicen.seminario.ddl.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieTrendingViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel(){

    private val _trendingMovies: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(PagingData.empty())
    val trendingMovies: StateFlow<PagingData<Movie>> = _trendingMovies.asStateFlow()
    fun getTrendingMovies(
    )   {
        viewModelScope.launch {
            movieRepository.getTrendingMovies().cachedIn(viewModelScope)
                .collectLatest { trendingMovies ->
                    _trendingMovies.value = trendingMovies
                }
        }
    }
}