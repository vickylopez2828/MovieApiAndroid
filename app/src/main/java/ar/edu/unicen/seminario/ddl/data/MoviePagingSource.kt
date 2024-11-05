package ar.edu.unicen.seminario.ddl.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import ar.edu.unicen.seminario.ddl.models.Movie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val api: MovieApi,
    private val movieType: MovieType
): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = when (movieType){
                MovieType.POPULAR -> api.getMovies(page = page)
                MovieType.TRENDING -> api.getTrendingMovies(page = page)
            }
            val movies = response.results
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (page < response.total_pages) page + 1  else null
            LoadResult.Page(data = movies.map { it.toMovie() }, prevKey = prevKey, nextKey = nextKey)
        } catch (e: IOException) {
            Log.e("MoviePagingSource", "Error loading movies: ${e.message}", e)
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.e("MoviePagingSource", "HTTP error: ${e.message}", e)
            return LoadResult.Error(e)
        } catch (e: Exception) {
            Log.e("MoviePagingSource", "Unexpected error: ${e.message}", e)
            LoadResult.Error(e)
        }
    }

}