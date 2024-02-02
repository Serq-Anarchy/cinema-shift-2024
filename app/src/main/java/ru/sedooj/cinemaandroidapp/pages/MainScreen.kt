package ru.sedooj.cinemaandroidapp.pages

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import ru.sedooj.cinemaandroidapp.navigation.SetupNavigation
import ru.sedooj.cinemaandroidapp.ui.design.navigation.BottomNavBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController
            )
        },
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            SetupNavigation(navController = navController, padding = padding.apply { PaddingValues(start = 10.dp, end = 10.dp) })
        }
    )
}