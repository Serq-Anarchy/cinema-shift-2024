package ru.sedooj.cinemaandroidapp.network.cinema

import ru.sedooj.cinemaandroidapp.network.Client

interface CinemaNetworkRepository : Client {

    suspend fun getAllTodayFilms() : AllTodayFilmsResponse?

}