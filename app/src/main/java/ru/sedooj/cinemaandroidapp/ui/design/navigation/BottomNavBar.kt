package ru.sedooj.cinemaandroidapp.ui.design.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.sedooj.cinemaandroidapp.navigation.Screens

@Composable
fun BottomNavBar(
    navController: NavController,
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        Screens.entries.forEach { page ->
            NavigationBarItem(
                selected = currentRoute == page.route,
                onClick = {
                    if (currentRoute != page.route) {
                        navController.navigate(route = page.route)
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(page.icon),
                        contentDescription = page.pageName,
                        modifier = Modifier.size(25.dp)
                    )
                },
                label = {
                    Text(text = page.pageName, color = MaterialTheme.colorScheme.onBackground)
                }
            )
        }
    }
}