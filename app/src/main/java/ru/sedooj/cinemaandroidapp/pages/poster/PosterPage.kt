package ru.sedooj.cinemaandroidapp.pages.poster

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.sedooj.cinemaandroidapp.navigation.Screens
import ru.sedooj.cinemaandroidapp.ui.design.pages.ScrollableCenteredScreenContentComponent

@Composable
fun PosterPage(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
) {
    ScrollableCenteredScreenContentComponent(
        modifier = modifier,
        mainPaddingValue = padding,
        title = Screens.POSTER.pageName,
        navigationIcon = {},
        content = {
            Text(text = Screens.POSTER.pageName)
        }
    )
}