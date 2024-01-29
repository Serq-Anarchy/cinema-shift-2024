package ru.sedooj.cinemaandroidapp.navigation

import ru.sedooj.cinemaandroidapp.R

enum class Screens(val route: String, val pageName: String, val icon: Int) {

    POSTER("POSTER_PAGE", "Афиша", icon = R.drawable.poster),
    TICKETS("TICKETS_PAGE", "Билеты", icon = R.drawable.ticket),
    PROFILE("PROFILE_PAGE", "Профиль", icon = R.drawable.user)

}