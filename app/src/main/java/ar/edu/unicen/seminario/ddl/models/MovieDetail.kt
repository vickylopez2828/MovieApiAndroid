package ar.edu.unicen.seminario.ddl.models

class MovieDetail(
    val id: Int,
    val title: String,
    val imageDetail: String?,
    val description: String,
    val rating: Float,
    val genres: List<String>
    )
{
}