package ru.sedooj.cinemaandroidapp.network.cinema

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CinemaImpl(
    private val client: HttpClient
) : Cinema {
    override suspend fun getAllTodayFilms(): AllTodayFilmsResponse? {
        val response = client.get("$defaultURL/cinema/today")
        val body = response.body<AllTodayFilmsResponse>()
        return AllTodayFilmsResponse(
            success = body.success,
            films = body.films.map { film ->
                AllTodayFilmsResponse.Film(
                    id = film.id,
                    name = film.name,
                    originalName = film.originalName,
                    description = film.description,
                    releaseDate = film.releaseDate,
                    actors = film.actors.map { actor ->
                        AllTodayFilmsResponse.Actor(
                            id = actor.id,
                            professions = actor.professions,
                            fullName = actor.fullName
                        )
                    },
                    directors = film.directors.map { director ->
                        AllTodayFilmsResponse.Director(
                            id = director.id,
                            professions = director.professions,
                            fullName = director.fullName
                        )
                    },
                    runtime = film.runtime,
                    ageRating = film.ageRating,
                    genres = film.genres,
                    userRatings = film.userRatings,
                    img = film.img

                )
            }
        )
    }


}