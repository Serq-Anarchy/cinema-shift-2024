package ru.sedooj.cinemaandroidapp.pages.poster.schedule

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import ru.sedooj.cinemaandroidapp.network.cinema.film.schedule.GetFilmScheduleByIdInput
import ru.sedooj.cinemaandroidapp.network.cinema.film.schedule.GetFilmScheduleByIdOutput
import ru.sedooj.cinemaandroidapp.network.cinema.repository.CinemaNetworkRepositoryImpl
import ru.sedooj.cinemaandroidapp.ui.design.pages.PageDataLoadingComponent
import ru.sedooj.cinemaandroidapp.ui.design.pages.ScrollableCenteredScreenContentComponent

fun String.translate(): String {
    return when (this) {
        "Red" -> "Красный зал"
        "Blue" -> "Синий зал"
        "Green" -> "Зелёный зал"
        else -> {
            this
        }
    }
}

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
                val client = remember { Client.create() }
                val cinemaNetworkRepository =
                    remember { CinemaNetworkRepositoryImpl(client = client) }

                FilmPreviewComponent(
                    filmId = filmId,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    cinemaNetworkRepository = cinemaNetworkRepository
                )

                ScheduleDataComponent(
                    filmId = filmId,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    cinemaNetworkRepository = cinemaNetworkRepository
                )

            }


        }
    )
}

val filmState = mutableStateOf<GetFilmByIdOutput?>(null)

@Composable
private fun FilmPreviewComponent(
    filmId: Long,
    modifier: Modifier,
    cinemaNetworkRepository: CinemaNetworkRepositoryImpl
) {
    if (filmState.value == null) {
        PageDataLoadingComponent(
            title = "Загрузка данных фильма..."
        )
        LaunchedEffect(key1 = filmState.value == null) {
            filmState.value =
                cinemaNetworkRepository.getFilmById(input = GetFilmByIdInput(id = filmId))
        }
    } else {
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

val scheduleState = mutableStateOf<GetFilmScheduleByIdOutput?>(null)

private data class SelectedHallTimeState(
    var hall: String = "",
    var time: String = "",
    var date: String = ""
)

@Composable
private fun ScheduleDataComponent(
    filmId: Long,
    modifier: Modifier,
    cinemaNetworkRepository: CinemaNetworkRepositoryImpl
) {
    val hallTime = remember { mutableStateOf(SelectedHallTimeState())}


    if (scheduleState.value == null) {
        PageDataLoadingComponent(
            title = "Загрузка расписания фильма..."
        )
        LaunchedEffect(key1 = scheduleState.value == null) {
            scheduleState.value = cinemaNetworkRepository.getFilmScheduleById(
                input = GetFilmScheduleByIdInput(
                    id = filmId
                )
            )
        }
    } else {
        val horizontalScrollState = rememberScrollState()
        val selectedDateState = remember { mutableStateOf(0) }

        SelectableDateComponent(
            modifier = Modifier.fillMaxWidth(),
            scheduleState = scheduleState.value,
            horizontalScrollState = horizontalScrollState,
            onSelect = {
                if (selectedDateState.value != it)
                    selectedDateState.value = it
            },
            selectedDate = selectedDateState.value
        )
        scheduleState.value?.schedules?.forEachIndexed { index, schedule ->
            if (selectedDateState.value == index) {
                var currentHall = ""
                schedule.seances.forEachIndexed { id, seance ->
                    if (currentHall != seance.hall.name) {
                        Text(seance.hall.name.translate())
                        currentHall = seance.hall.name
                    }

                    SelectableButtonComponent(
                        onClick = {
                            if (hallTime.value.time != seance.time) {
                                hallTime.value = SelectedHallTimeState(
                                    time = seance.time,
                                    hall = seance.hall.name,
                                    date = schedule.date
                                )
                            }
                        },
                        text = seance.time,
                        isSelected = hallTime.value.time == seance.time && hallTime.value.date == schedule.date,
                        colors = SelectableButtonColors(
                            selected = MaterialTheme.colorScheme.inversePrimary,
                            notSelected = Color.LightGray
                        )
                    )
                }
            }
        }
    }
}

private data class SelectableButtonColors(
    val selected: Color = Color.DarkGray,
    val notSelected: Color = Color.LightGray,
    val disabled: Color = Color.Unspecified
)


@Composable
private fun SelectableDateComponent(
    modifier: Modifier,
    scheduleState: GetFilmScheduleByIdOutput?,
    horizontalScrollState: ScrollState,
    onSelect: (Int) -> Unit,
    selectedDate: Int
) {
    Text(
        text = "Даты",
        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
        fontWeight = FontWeight.ExtraBold,
    )
    Card(
        modifier = modifier
            .wrapContentHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.horizontalScroll(state = horizontalScrollState)
        ) {
            scheduleState?.schedules?.forEachIndexed { index, schedule ->
                SelectableButtonComponent(
                    onClick = {
                        onSelect(index)
                    },
                    text = schedule.date,
                    isSelected = selectedDate == index,
                    colors = SelectableButtonColors(
                        selected = MaterialTheme.colorScheme.background,
                        notSelected = Color.Unspecified
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectableButtonComponent(
    onClick: () -> Unit,
    text: String,
    isSelected: Boolean,
    colors: SelectableButtonColors
) {
    Card(
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .height(40.dp)
            .padding(3.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) colors.selected else colors.notSelected,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        onClick = {
            onClick()
        },
        content = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = text, textAlign = TextAlign.Center)
            }
        }
    )
}