package ru.sedooj.cinemaandroidapp.network.cinema

import kotlinx.serialization.Serializable

@Serializable
data class AllTodayFilmsOutput(
    val success: Boolean,
    val films: List<Film>
) {

    @Serializable
    data class Film(
        val id: Long,
        val name: String,
        val originalName: String,
        val description: String,
        val releaseDate: String,
        val actors: List<Actor>,
        val directors: List<Director>,
        val runtime: Int,
        val ageRating: String,
        val genres: List<String>,
        val userRatings: Map<String, String>,
        val img: String,
    )

    @Serializable
    data class Actor(
        val id: Long,
        val professions: List<String>,
        val fullName: String
    )
    @Serializable
    data class Director(
        val id: Long,
        val professions: List<String>,
        val fullName: String
    )
}
