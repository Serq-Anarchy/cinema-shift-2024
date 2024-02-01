package ru.sedooj.cinemaandroidapp.pages.poster.schedule

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import ru.sedooj.cinemaandroidapp.R
import ru.sedooj.cinemaandroidapp.navigation.Screens
import ru.sedooj.cinemaandroidapp.ui.design.pages.ScrollableCenteredScreenContentComponent

@Composable
fun SchedulePage(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    filmId: Long?,
    navController: NavController
) {
    ScrollableCenteredScreenContentComponent(
        modifier = modifier,
        mainPaddingValue = padding,
        title = Screens.SCHEDULE.pageName,
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Back",
                    )
                })

        },
        content = {
            Text(text = "$filmId")
        }
    )
}