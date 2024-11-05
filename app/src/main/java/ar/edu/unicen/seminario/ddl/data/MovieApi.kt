package ar.edu.unicen.seminario.ddl.data

import ar.edu.unicen.seminario.BuildConfig
import ar.edu.unicen.seminario.common.Constants
import ar.edu.unicen.seminario.ddl.data.dto.MovieDetailDto
import ar.edu.unicen.seminario.ddl.data.dto.MovieDto
import ar.edu.unicen.seminario.ddl.data.dto.MovieResultDto
import ar.edu.unicen.seminario.ddl.data.dto.ResponseWrapper
import ar.edu.unicen.seminario.ddl.models.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page : Int
    ) : ResponseWrapper

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieDetailDto>

    @GET("movie/{id}/similar")
    suspend fun getSimilarMovies(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieResultDto>

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page : Int
    ) : ResponseWrapper

}