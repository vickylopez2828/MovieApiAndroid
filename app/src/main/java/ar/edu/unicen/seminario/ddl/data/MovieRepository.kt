package ar.edu.unicen.seminario.ddl.data

import android.util.Log
import androidx.paging.PagingData
import ar.edu.unicen.seminario.ddl.models.Movie
import ar.edu.unicen.seminario.ddl.models.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) {
     suspend fun getMovies(): Flow<PagingData<Movie>> {
        return remoteDataSource.getMovies()

    }
    suspend fun getSimilarMovies(id: Int): List<Movie>? {
        return remoteDataSource.getSimilarMovies(id)
    }
    suspend fun getMovie(id:Int): MovieDetail? {
        return remoteDataSource.getMovie(id)
    }
    suspend fun getTrendingMovies(): Flow<PagingData<Movie>>{
        return remoteDataSource.getTrendingMovies()
    }


}