package ru.sedooj.cinemaandroidapp.pages.poster

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import ru.sedooj.cinemaandroidapp.R
import ru.sedooj.cinemaandroidapp.navigation.Screens
import ru.sedooj.cinemaandroidapp.ui.design.pages.NavigationIconComponent
import ru.sedooj.cinemaandroidapp.ui.design.pages.ScrollableCenteredScreenContentComponent

@Composable
fun PosterPage(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    navController: NavController
) {
    ScrollableCenteredScreenContentComponent(
        modifier = modifier,
        mainPaddingValue = padding,
        title = Screens.POSTER.pageName,
        navigationIcon = {
            NavigationIconComponent(
                navController = navController,
                icon = painterResource(id = R.drawable.arrow_back),
                description = "Back"
            )
        },
        content = {
            Text(text = Screens.POSTER.pageName)
        }
    )
}