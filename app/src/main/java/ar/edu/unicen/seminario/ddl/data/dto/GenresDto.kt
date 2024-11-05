package ar.edu.unicen.seminario.ddl.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class GenresDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
) {
}