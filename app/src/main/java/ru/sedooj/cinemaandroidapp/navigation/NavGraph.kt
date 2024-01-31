package ru.sedooj.cinemaandroidapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
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
    val noEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
        {
            fadeIn(
                animationSpec = tween(durationMillis = 300),
                initialAlpha = 0.99f
            )
        }
    val noExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        fadeOut(
            animationSpec = tween(durationMillis = 300),
            targetAlpha = 0.99f
        )
    }

    NavHost(
        navController = navController,
        startDestination = Screens.POSTER.route,
        exitTransition = {
            noExitTransition()
        },
        enterTransition = {
            noEnterTransition()
        },
        popExitTransition = {
            noExitTransition()
        },
        popEnterTransition = {
            noEnterTransition()
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