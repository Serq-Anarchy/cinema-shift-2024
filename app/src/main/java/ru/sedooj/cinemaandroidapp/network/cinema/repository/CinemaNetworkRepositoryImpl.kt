package ru.sedooj.cinemaandroidapp.network.cinema.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.sedooj.cinemaandroidapp.network.Data
import ru.sedooj.cinemaandroidapp.network.cinema.film.GetFilmByIdInput
import ru.sedooj.cinemaandroidapp.network.cinema.film.GetFilmByIdOutput
import ru.sedooj.cinemaandroidapp.network.cinema.film.schedule.GetFilmScheduleByIdInput
import ru.sedooj.cinemaandroidapp.network.cinema.film.schedule.GetFilmScheduleByIdOutput
import ru.sedooj.cinemaandroidapp.network.cinema.todayFilms.AllTodayFilmsOutput

class CinemaNetworkRepositoryImpl(
    private val client: HttpClient
) : CinemaNetworkRepository {
    override suspend fun getAllTodayFilms(): AllTodayFilmsOutput? {
        val response = client.get("${Data.BASE_URL}/cinema/today")
        val body = response.body<AllTodayFilmsOutput>()
        return AllTodayFilmsOutput(
            success = body.success,
            films = body.films.map { film ->
                AllTodayFilmsOutput.Film(
                    id = film.id,
                    name = film.name,
                    originalName = film.originalName,
                    description = film.description,
                    releaseDate = film.releaseDate,
                    actors = film.actors.map { actor ->
                        AllTodayFilmsOutput.Actor(
                            id = actor.id,
                            professions = actor.professions,
                            fullName = actor.fullName
                        )
                    },
                    directors = film.directors.map { director ->
                        AllTodayFilmsOutput.Director(
                            id = director.id,
                            professions = director.professions,
                            fullName = director.fullName
                        )
                    },
                    runtime = film.runtime,
                    ageRating = film.ageRating,
                    genres = film.genres,
                    userRatings = film.userRatings,
                    img = film.img,
                    country = AllTodayFilmsOutput.Country(
                        id = film.country.id,
                        code = film.country.code,
                        code2 = film.country.code2,
                        name = film.country.name
                    )

                )
            }
        )
    }

    override suspend fun getFilmById(input: GetFilmByIdInput): GetFilmByIdOutput? {
        val response = client.get("${Data.BASE_URL}/cinema/film/${input.id}")
        val body = response.body<GetFilmByIdOutput>()
        return GetFilmByIdOutput(
            success = body.success,
            film = GetFilmByIdOutput.Film(
                id = body.film.id,
                name = body.film.name,
                originalName = body.film.originalName,
                description = body.film.description,
                releaseDate = body.film.releaseDate,
                actors = body.film.actors.map { actor ->
                    GetFilmByIdOutput.Actor(
                        id = actor.id,
                        professions = actor.professions,
                        fullName = actor.fullName
                    )
                },
                directors = body.film.directors.map { director ->
                    GetFilmByIdOutput.Director(
                        id = director.id,
                        professions = director.professions,
                        fullName = director.fullName
                    )
                },
                runtime = body.film.runtime,
                ageRating = body.film.ageRating,
                genres = body.film.genres,
                userRatings = body.film.userRatings,
                img = body.film.img,
                country = GetFilmByIdOutput.Country(
                    id = body.film.country.id,
                    code = body.film.country.code,
                    code2 = body.film.country.code2,
                    name = body.film.country.name

                )
            )
        )
    }

    override suspend fun getFilmScheduleById(input: GetFilmScheduleByIdInput): GetFilmScheduleByIdOutput? {
        val response = client.get("${Data.BASE_URL}/cinema/film/${input.id}/schedule")
        val body = response.body<GetFilmScheduleByIdOutput>()
        return GetFilmScheduleByIdOutput(
            success = body.success,
            schedules = body.schedules.map { schedule ->
                GetFilmScheduleByIdOutput.Schedule(
                    date = schedule.date,
                    seances = schedule.seances.map { seance ->
                        GetFilmScheduleByIdOutput.Seance(
                            time = seance.time,
                            hall = GetFilmScheduleByIdOutput.Hall(
                                name = seance.hall.name,
                                places = seance.hall.places.map {
                                    it.map { place ->
                                        GetFilmScheduleByIdOutput.Place(
                                            type = place.type,
                                            price = place.price
                                        )
                                    }
                                }
                            ),
                            payedTickets = seance.payedTickets.map { payedTicket ->
                                GetFilmScheduleByIdOutput.Place(
                                    price = payedTicket.price,
                                    type = payedTicket.type
                                )
                            }
                        )
                    }
                )
            }
        )
    }
}