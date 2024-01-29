package ru.sedooj.cinemaandroidapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.sedooj.cinemaandroidapp.pages.poster.PosterPage
import ru.sedooj.cinemaandroidapp.pages.profile.ProfilePage
import ru.sedooj.cinemaandroidapp.pages.tickets.TicketsPage

@Composable
fun SetupNavigation(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screens.POSTER.route,
        exitTransition = {
            fadeOut(tween(0))
        },
        enterTransition = {
            fadeIn(tween(0))
        },
        popEnterTransition = {
            fadeIn(tween(0))
        },
        popExitTransition = {
            fadeOut(tween(0))
        }
    ) {
        composable(
            route = Screens.POSTER.route
        ) {
            PosterPage(padding = padding)
        }

        composable(
            route = Screens.TICKETS.route
        ) {
            TicketsPage(padding = padding)
        }

        composable(
            route = Screens.PROFILE.route
        ) {
            ProfilePage(padding = padding)
        }

    }
}