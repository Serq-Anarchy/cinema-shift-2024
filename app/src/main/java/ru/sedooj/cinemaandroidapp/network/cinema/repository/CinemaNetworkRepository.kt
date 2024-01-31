package ru.sedooj.cinemaandroidapp.network.cinema.repository

import ru.sedooj.cinemaandroidapp.network.Client
import ru.sedooj.cinemaandroidapp.network.cinema.film.GetFilmByIdInput
import ru.sedooj.cinemaandroidapp.network.cinema.film.GetFilmByIdOutput
import ru.sedooj.cinemaandroidapp.network.cinema.film.schedule.GetFilmScheduleByIdInput
import ru.sedooj.cinemaandroidapp.network.cinema.film.schedule.GetFilmScheduleByIdOutput
import ru.sedooj.cinemaandroidapp.network.cinema.todayFilms.AllTodayFilmsOutput

interface CinemaNetworkRepository : Client {

    suspend fun getAllTodayFilms() : AllTodayFilmsOutput?

    suspend fun getFilmById(input: GetFilmByIdInput): GetFilmByIdOutput?

    suspend fun getFilmScheduleById(input: GetFilmScheduleByIdInput): GetFilmScheduleByIdOutput?
}