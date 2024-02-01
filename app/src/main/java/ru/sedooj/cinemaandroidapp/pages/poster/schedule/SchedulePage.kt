package ru.sedooj.cinemaandroidapp.pages.poster.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ru.sedooj.cinemaandroidapp.R
import ru.sedooj.cinemaandroidapp.navigation.Screens
import ru.sedooj.cinemaandroidapp.network.Client
import ru.sedooj.cinemaandroidapp.network.Data
import ru.sedooj.cinemaandroidapp.network.cinema.film.GetFilmByIdInput
import ru.sedooj.cinemaandroidapp.network.cinema.film.GetFilmByIdOutput
import ru.sedooj.cinemaandroidapp.network.cinema.repository.CinemaNetworkRepositoryImpl
import ru.sedooj.cinemaandroidapp.ui.design.pages.PageDataLoadingComponent
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
            if (filmId != null) {
                FilmPreviewComponent(
                    filmId = filmId,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                )

            }


        }
    )
}

@Composable
private fun FilmPreviewComponent(
    filmId: Long,
    modifier: Modifier
) {
    val client = remember { Client.create() }
    val filmState = remember { mutableStateOf<GetFilmByIdOutput?>(null) }
    val cinemaNetworkRepository = remember { CinemaNetworkRepositoryImpl(client = client) }

    LaunchedEffect(key1 = true) {
        filmState.value = cinemaNetworkRepository.getFilmById(input = GetFilmByIdInput(id = filmId))
    }

    if (filmState.value != null) {
        Text(
            text = "Фильм",
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.ExtraBold,
        )
        Card(
            modifier = modifier,
            content = {
                filmState.value?.film?.let { film ->
                    FilmDataComponent(
                        img = film.img, title = film.name, description = film.description
                    )
                }
            }

        )
    } else {
        PageDataLoadingComponent(
            title = "Загрузка данных фильма..."
        )
    }


}

@Composable
private fun FilmDataComponent(
    img: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            // Poster
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center,
                content = {
                    AsyncImage(
                        model = "${Data.BASE_URL}$img",
                        contentDescription = "Image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp)
                            .sizeIn(
                                minWidth = 50.dp,
                                minHeight = 50.dp,
                                maxHeight = 100.dp,
                                maxWidth = 200.dp
                            )
                            .clip(shape = RoundedCornerShape(15.dp)),
                    )
                }
            )
            // Data
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(4f),
                contentAlignment = Alignment.Center,
                content = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        // Title
                        Text(
                            text = title,
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            fontWeight = FontWeight.ExtraBold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        // Description
                        Text(
                            text = description,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            color = Color.DarkGray,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 3
                        )
                    }
                }
            )

        }
    )
}