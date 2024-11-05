package ar.edu.unicen.seminario.ddl.data.dto

import androidx.annotation.Keep
import ar.edu.unicen.seminario.ddl.models.Movie
import ar.edu.unicen.seminario.ddl.models.MovieDetail
import com.google.gson.annotations.SerializedName
@Keep
data class MovieDetailDto (
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("backdrop_path")
    val imageDetail: String?,
    @SerializedName("overview")
    val description: String,
    @SerializedName("vote_average")
    val rating: Float,
    @SerializedName("genres")
    val genres: List<GenresDto>
){
    fun toMovieDetail(): MovieDetail {
        //val img = "https://image.tmdb.org/t/p/w500" + imageDetail
        return MovieDetail(
            id = id,
            title = title,
            imageDetail = imageDetail,
            description = description,
            rating = rating,
            genres = listGenres()
        )
    }

    fun listGenres(): List<String>{
        val rdo: MutableList<String> = mutableListOf()
        for (it in genres){
            rdo.add(it.name)
        }
        return rdo
    }
}