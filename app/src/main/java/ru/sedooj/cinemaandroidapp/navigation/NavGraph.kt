package ru.sedooj.cinemaandroidapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.sedooj.cinemaandroidapp.pages.poster.PosterPage
import ru.sedooj.cinemaandroidapp.pages.poster.schedule.CardDetailsPage
import ru.sedooj.cinemaandroidapp.pages.poster.schedule.ChoosePositionPage
import ru.sedooj.cinemaandroidapp.pages.poster.schedule.SchedulePage
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
        composable(route = Screens.POSTER.route) {
            PostersNavHost(padding = padding)
        }

        composable(
            route = Screens.TICKETS.route
        ) {
            TicketsNavHost(padding = padding)
        }

        composable(
            route = Screens.PROFILE.route
        ) {
            ProfileNavHost(padding = padding)
        }

    }
}

@Composable
fun PostersNavHost(
    padding: PaddingValues
) {
    val postersNavController = rememberNavController()
    NavHost(
        navController = postersNavController,
        startDestination = Screens.POSTER.route
    ) {
        composable(
            route = Screens.POSTER.route
        ) {
            PosterPage(padding = padding, navController = postersNavController)
        }

        composable(
            route = "${Screens.SCHEDULE.route}/{filmId}",
            arguments = listOf(navArgument("filmId") { type = NavType.LongType })
        ) {
            SchedulePage(
                padding = padding,
                filmId = postersNavController.currentBackStackEntry?.arguments?.getLong("filmId"),
                navController = postersNavController
            )
        }
        composable(
            route = Screens.POSITION.route
        ) {
            ChoosePositionPage(
                modifier = Modifier.fillMaxSize(),
                padding = padding,
                navController = postersNavController
            )
        }
        composable(
            route = Screens.CARD_DETAILS.route
        ) {
            CardDetailsPage(
                modifier = Modifier.fillMaxSize(),
                paddingValues = padding,
                navController = postersNavController
            )
        }

    }
}

@Composable
fun TicketsNavHost(
    padding: PaddingValues
) {
    val ticketsNavController = rememberNavController()
    NavHost(
        navController = ticketsNavController,
        startDestination = Screens.TICKETS.route
    ) {
        composable(
            route = Screens.TICKETS.route
        ) {
            TicketsPage(padding = padding)
        }
    }
}

@Composable
fun ProfileNavHost(
    padding: PaddingValues
) {
    val profileNavController = rememberNavController()
    NavHost(
        navController = profileNavController,
        startDestination = Screens.PROFILE.route
    ) {
        composable(
            route = Screens.PROFILE.route
        ) {
            ProfilePage(padding = padding)
        }
    }
}