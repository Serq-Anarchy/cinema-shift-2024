package ru.sedooj.cinemaandroidapp.network.cinema

import ru.sedooj.cinemaandroidapp.network.Client

interface Cinema : Client {

    suspend fun getAllTodayFilms() : AllTodayFilmsResponse?

}