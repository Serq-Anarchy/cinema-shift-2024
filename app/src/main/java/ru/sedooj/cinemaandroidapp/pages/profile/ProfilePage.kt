package ru.sedooj.cinemaandroidapp.pages.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.sedooj.cinemaandroidapp.navigation.Screens
import ru.sedooj.cinemaandroidapp.ui.design.pages.ScrollableCenteredScreenContentComponent

@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
) {
    ScrollableCenteredScreenContentComponent(
        modifier = modifier,
        mainPaddingValue = padding,
        title = Screens.PROFILE.pageName,
        navigationIcon = {},
        content = {
            androidx.compose.material3.Text(text = Screens.PROFILE.pageName)
        }
    )
}