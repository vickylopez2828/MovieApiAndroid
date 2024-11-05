package ar.edu.unicen.seminario.ddl.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSourceFactory
import ar.edu.unicen.seminario.ddl.models.Movie
import ar.edu.unicen.seminario.ddl.models.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieApi: MovieApi
) {
    companion object{
        const val MAX_ITEMS = 10
        const val PREFETCH_ITEMS = 3
    }

    suspend fun getMovies(): Flow<PagingData<Movie>>{
        return Pager(config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
        pagingSourceFactory = {
            MoviePagingSource(movieApi, MovieType.POPULAR)
        }).flow
    }

    suspend fun getTrendingMovies(): Flow<PagingData<Movie>>{
        return Pager(config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                MoviePagingSource(movieApi, MovieType.TRENDING)
            }).flow
    }

    suspend fun getSimilarMovies(
        id:Int
    ): List<Movie>?{
        return withContext(Dispatchers.IO){
            try {
                val response = movieApi.getSimilarMovies(id)

                return@withContext response.body()?.results?.map{ it.toMovie()}
            } catch (e:Exception){
                e.printStackTrace()
                return@withContext null
            }
        }

    }

    suspend fun getMovie(
        id: Int
    ): MovieDetail?{
        return withContext(Dispatchers.IO){
            try {
                val response = movieApi.getMovie(id)

                return@withContext response.body()?.toMovieDetail()
            } catch (e:Exception){
                e.printStackTrace()
                return@withContext null
            }
        }

    }
//    suspend fun getTrendingMovies(
//
//    ): List<Movie>?{
//        return withContext(Dispatchers.IO){
//            try {
//                val response = movieApi.getTrendingMovies()
//
//                return@withContext response.body()?.results?.map{ it.toMovie()}
//            } catch (e:Exception){
//                e.printStackTrace()
//                return@withContext null
//            }
//        }
//
//    }

}