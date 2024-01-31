package ru.sedooj.cinemaandroidapp.network.cinema.film.schedule

import androidx.collection.ArrayMap
import kotlinx.serialization.Serializable

@Serializable
data class GetFilmScheduleByIdOutput(
    val success: Boolean,
    val schedules: List<Schedule>
) {

    @Serializable
    data class Schedule(
        val date: String,
        val seances: List<Seance>
    )

    @Serializable
    data class Seance(
        val time: String,
        val hall: Hall,
        val payedTickets: List<Place>
    )

    @Serializable
    data class Hall(
        val name: String,
        val places: List<List<Place>>
    )

    @Serializable
    data class Place(
        val price: Int,
        val type: String
    )

}
