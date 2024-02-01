package ru.sedooj.cinemaandroidapp.pages.poster.schedule

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
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

@Composable
private fun FilmPreviewComponent(
    filmId: Long,
    modifier: Modifier,
    cinemaNetworkRepository: CinemaNetworkRepositoryImpl
) {

    val filmState = remember { mutableStateOf<GetFilmByIdOutput?>(null) }

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

@Composable
private fun ScheduleDataComponent(
    filmId: Long,
    modifier: Modifier,
    cinemaNetworkRepository: CinemaNetworkRepositoryImpl
) {
    val scheduleState = remember { mutableStateOf<GetFilmScheduleByIdOutput?>(null) }
    LaunchedEffect(key1 = true) {
        scheduleState.value = cinemaNetworkRepository.getFilmScheduleById(
            input = GetFilmScheduleByIdInput(
                id = filmId
            )
        )
    }

    if (scheduleState.value != null) {
        val horizontalScrollState = rememberScrollState()
        val selectedDateState = remember { mutableStateOf(0) }
        Text(
            text = "Даты",
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.ExtraBold,
        )
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .padding(start = 10.dp, end = 10.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.horizontalScroll(state = horizontalScrollState)
            ) {
                scheduleState.value?.schedules?.forEachIndexed { index, schedule ->
                    SelectableButtonComponent(
                        onClick = {
                            if (selectedDateState.value != index)
                                selectedDateState.value = index
                        },
                        text = schedule.date,
                        isSelected = selectedDateState.value == index,
                        colors = SelectableButtonColors(
                            selected = MaterialTheme.colorScheme.background,
                            notSelected = Color.Unspecified
                        )
                    )
                }
            }
        }

        val redHallScheduleTimeList = remember { mutableListOf<String>() }
        val blueHallScheduleTimeList = remember { mutableListOf<String>() }
        val greenHallScheduleTimeList = remember { mutableListOf<String>() }

        val selectedRedHallTime = remember { mutableStateOf(-1) }
        val selectedBlueHallTime = remember { mutableStateOf(-1) }
        val selectedGreenHallTime = remember { mutableStateOf(-1) }

        scheduleState.value?.schedules?.forEachIndexed { index, schedule ->
            if (selectedDateState.value == index) {
                schedule.seances.forEach { seance ->
                    when (seance.hall.name) {
                        "Red" ->
                            redHallScheduleTimeList.add(seance.time)

                        "Blue" ->
                            blueHallScheduleTimeList.add(seance.time)

                        "Green" ->
                            greenHallScheduleTimeList.add(seance.time)
                    }

//                    val selectedTimeState = remember { mutableStateOf(-1) }
//
//                    if (currentHall.value != seance.hall.name)
//                        Text(
//                            text = seance.hall.name,
//                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
//                            fontWeight = FontWeight.ExtraBold,
//                        )
//
//                    currentHall.value = seance.hall.name
//
//                    SelectableButtonComponent(
//                        text = seance.time,
//                        onClick = {
//                            selectedTimeState.value = index
//                        },
//                        isSelected = selectedDateState.value == index
//                    )
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (redHallScheduleTimeList.isNotEmpty())
                    HallWithTimeComponent(
                        title = "Red",
                        items = redHallScheduleTimeList,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (selectedRedHallTime.value != it)
                                selectedRedHallTime.value = it
                        },
                        selectedItemIndex = selectedRedHallTime.value,
                        colors = SelectableButtonColors(
                            selected = MaterialTheme.colorScheme.primary,
                            notSelected = Color.LightGray
                        )
                    )
                    HallWithTimeComponent(
                        title = "Blue",
                        items = blueHallScheduleTimeList,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (selectedBlueHallTime.value != it)
                                selectedBlueHallTime.value = it
                        },
                        selectedItemIndex = selectedBlueHallTime.value,
                        colors = SelectableButtonColors(
                            selected = MaterialTheme.colorScheme.primary,
                            notSelected = Color.LightGray
                        )
                    )
                    HallWithTimeComponent(
                        title = "Green",
                        items = greenHallScheduleTimeList,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (selectedGreenHallTime.value != it)
                                selectedGreenHallTime.value = it
                        },
                        selectedItemIndex = selectedGreenHallTime.value,
                        colors = SelectableButtonColors(
                            selected = MaterialTheme.colorScheme.primary,
                            notSelected = Color.LightGray
                        )
                    )
                }
            }
        )
    } else {
        PageDataLoadingComponent(
            title = "Загрузка расписания фильма..."
        )
    }
}

private data class SelectableButtonColors(
    val selected: Color = Color.DarkGray,
    val notSelected: Color = Color.LightGray,
    val disabled: Color = Color.Unspecified
)

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
            .width(80.dp),
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HallWithTimeComponent(
    title: String,
    items: List<String>,
    modifier: Modifier,
    onClick: (Int) -> Unit,
    selectedItemIndex: Int,
    colors: SelectableButtonColors
) {
    Column(modifier = modifier) {
        Text(text = title)
        FlowRow(
            maxItemsInEachRow = 4
        ) {
            items.forEachIndexed { index, it ->
                SelectableButtonComponent(
                    onClick = {
                        onClick(index)
                    },
                    text = it,
                    isSelected = selectedItemIndex == index,
                    colors = colors
                )
            }

        }

    }
}