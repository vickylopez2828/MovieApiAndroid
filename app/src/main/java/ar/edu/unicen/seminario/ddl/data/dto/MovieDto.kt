package ar.edu.unicen.seminario.ddl.data.dto

import android.util.Log
import androidx.annotation.Keep
import ar.edu.unicen.seminario.R
import ar.edu.unicen.seminario.ddl.models.Genres
import ar.edu.unicen.seminario.ddl.models.Movie
import com.google.gson.annotations.SerializedName
@Keep
data class MovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val image: String,
    @SerializedName("backdrop_path")
    val alternativeImage: String,

) {
    fun toMovie(): Movie {
        var imageUrl = image
        if (image == null  && alternativeImage != null){
            imageUrl = alternativeImage
        }
        return Movie(
            id = id,
            title = title,
            image = imageUrl
        )
    }


}
