package ar.edu.unicen.seminario.ddl.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ResponseWrapper(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val total_pages: Int,
)