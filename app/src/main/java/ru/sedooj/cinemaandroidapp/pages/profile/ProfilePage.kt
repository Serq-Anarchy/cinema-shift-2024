package ru.sedooj.cinemaandroidapp.pages.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import ru.sedooj.cinemaandroidapp.R
import ru.sedooj.cinemaandroidapp.navigation.Screens
import ru.sedooj.cinemaandroidapp.ui.design.pages.NavigationIconComponent
import ru.sedooj.cinemaandroidapp.ui.design.pages.ScrollableCenteredScreenContentComponent

@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    navController: NavController
) {
    ScrollableCenteredScreenContentComponent(
        modifier = modifier,
        mainPaddingValue = padding,
        title = Screens.PROFILE.pageName,
        navigationIcon = {
            NavigationIconComponent(
                navController = navController,
                icon = painterResource(id = R.drawable.arrow_back),
                description = "Back"
            )
        },
        content = {
            androidx.compose.material3.Text(text = Screens.PROFILE.pageName)
        }
    )
}