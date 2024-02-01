package ru.sedooj.cinemaandroidapp.navigation

import ru.sedooj.cinemaandroidapp.R


enum class Screens(val route: String, val pageName: String, val icon: Int, val isBottomBarPage: Boolean) {

    POSTER("POSTER_PAGE", "Афиша", icon = R.drawable.poster, isBottomBarPage = true),
    TICKETS("TICKETS_PAGE", "Билеты", icon = R.drawable.ticket, isBottomBarPage = true),
    PROFILE("PROFILE_PAGE", "Профиль", icon = R.drawable.user, isBottomBarPage = true),
    SCHEDULE("SCHEDULE_PAGE", "Расписание", icon = R.drawable.poster, isBottomBarPage = false)
}