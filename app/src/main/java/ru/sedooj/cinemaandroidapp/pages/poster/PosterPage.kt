package ru.sedooj.cinemaandroidapp.pages.poster

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.AsyncImage
import ru.sedooj.cinemaandroidapp.R
import ru.sedooj.cinemaandroidapp.navigation.Screens
import ru.sedooj.cinemaandroidapp.network.Client
import ru.sedooj.cinemaandroidapp.network.Data
import ru.sedooj.cinemaandroidapp.network.cinema.repository.CinemaNetworkRepositoryImpl
import ru.sedooj.cinemaandroidapp.network.cinema.todayFilms.AllTodayFilmsOutput
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
        navigationIcon = {},
        content = {
            val client = remember { Client.create() }
            val state = remember { mutableStateOf<AllTodayFilmsOutput?>(null) }
            val cinemaNetworkRepository = remember { CinemaNetworkRepositoryImpl(client = client) }

            LaunchedEffect(key1 = true) {
                state.value = cinemaNetworkRepository.getAllTodayFilms()
            }

            if (state.value != null) {
                state.value?.films?.forEach { film ->
                    FilmItem(
                        name = film.name,
                        originalName = film.originalName,
                        description = film.description,
                        releaseDate = film.releaseDate,
                        actors = film.actors,
                        directors = film.directors,
                        runtime = film.runtime,
                        ageRating = film.ageRating,
                        genres = film.genres,
                        userRatings = film.userRatings,
                        img = film.img,
                        onSchedule = {
                            navController.navigate("${Screens.SCHEDULE.route}/${film.id}") {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        country = film.country.name
                    )
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            androidx.compose.material.CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                            Text(
                                text = "Загрузка списка фильмов...",
                                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )

            }
        }
    )
}

@Composable
private fun FilmItem(
    name: String,
    originalName: String,
    description: String,
    releaseDate: String,
    actors: List<AllTodayFilmsOutput.Actor>,
    directors: List<AllTodayFilmsOutput.Director>,
    runtime: Int,
    ageRating: String,
    genres: List<String>,
    userRatings: Map<String, String>,
    img: String,
    onSchedule: () -> Unit,
    modifier: Modifier,
    country: String
) {

    val isExpanded = remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        content = {
            PreviewCardComponent(
                img = img,
                title = name,
                subtitle = originalName,
                userRatings = userRatings
            )
            this.AnimatedVisibility(visible = isExpanded.value) {
                DetailsCardBarComponent(
                    onClick = { isExpanded.value = false },
                    description = description,
                    releaseDate = releaseDate,
                    runtime = runtime,
                    actors = actors,
                    directors = directors,
                    genres = genres,
                    ageRating = ageRating,
                    onSchedule = { onSchedule() },
                    country = country
                )
            }
            this.AnimatedVisibility(visible = !isExpanded.value) {
                ExpandCardBarComponent(
                    onClick = {
                        isExpanded.value = true
                    }
                )
            }
        }
    )
}


@Composable
private fun PreviewCardComponent(
    img: String,
    title: String,
    subtitle: String,
    userRatings: Map<String, String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = {
                    AsyncImage(
                        model = "${Data.BASE_URL}$img",
                        contentDescription = "Image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                            .sizeIn(
                                minWidth = 450.dp,
                                minHeight = 630.dp,
                                maxHeight = 450.dp,
                                maxWidth = 630.dp
                            )
                            .clip(shape = RoundedCornerShape(15.dp)),
                    )
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Column(
                        modifier = Modifier.weight(5f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        // Title
                        Text(
                            text = title,
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            fontWeight = FontWeight.ExtraBold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        // Subtitle
                        Text(
                            text = subtitle,
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            color = Color.DarkGray,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                    // User ratings
                    Column(
                        modifier = Modifier.weight(5f),
                        horizontalAlignment = Alignment.End
                    ) {
                        userRatings.forEach { rating ->
                            Text(
                                text = "${rating.key} - ${rating.value}".capitalize(),
                                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            )
                        }
                    }
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HorizontalListCardComponent(
    title: String,
    list: List<String>,
    icon: Painter? = null
) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
        fontWeight = FontWeight.Bold
    )

    val horizontalScrollState = rememberScrollState()
    Row(
        modifier = Modifier.horizontalScroll(horizontalScrollState),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            list.forEach { item ->
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(3.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        /*TODO: Открыть страницу чего-либо*/
                    }
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(3.dp)
                    ) {
                        if (icon != null) {
                            Icon(
                                painter = icon,
                                contentDescription = "Icon"
                            )
                        }
                        Text(text = item)
                    }
                }
            }
        }
    )
}

@Composable
private fun ExpandCardBarComponent(
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(size = 15.dp),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp),
        content = {
            Text(
                text = "Подробнее",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    )
}

@Composable
private fun DetailsCardBarComponent(
    onClick: () -> Unit,
    description: String,
    releaseDate: String,
    runtime: Int,
    ageRating: String,
    actors: List<AllTodayFilmsOutput.Actor>,
    directors: List<AllTodayFilmsOutput.Director>,
    genres: List<String>,
    onSchedule: () -> Unit,
    country: String
) {
    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start,
        content = {
            Text(text = "Страна: $country", fontWeight = FontWeight.Bold)
            Text(text = "Возрастные ограничения: $ageRating", textAlign = TextAlign.End)
            Text(
                text = description,
                fontStyle = MaterialTheme.typography.headlineSmall.fontStyle
            )
            Text(
                text = "Дата выхода: $releaseDate",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Длительность: $runtime мин.",
                fontWeight = FontWeight.Bold
            )

            HorizontalListCardComponent(
                title = "Жанры:",
                list = genres
            )

            HorizontalListCardComponent(
                title = "В фильме снимались:",
                list = actors.map { actor ->
                    actor.fullName
                },
                icon = painterResource(id = R.drawable.user)
            )

            HorizontalListCardComponent(
                title = "Создатели:",
                list = directors.map { director ->
                    director.fullName
                },
                icon = painterResource(id = R.drawable.user)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    // Roll up
                    IconButton(
                        onClick = { onClick() },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp),
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_back),
                                contentDescription = "Roll up",
                                modifier = Modifier.rotate(90f)
                            )
                        })
                    // Watch schedule
                    Button(
                        onClick = { onSchedule() },
                        shape = RoundedCornerShape(size = 15.dp),
                        modifier = Modifier
                            .weight(5f)
                            .padding(end = 10.dp),
                        content = {
                            Text(
                                text = "Посмотреть расписание",
                                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    )

                }
            )
        }
    )
}