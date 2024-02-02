package ru.sedooj.cinemaandroidapp.pages.poster.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.sedooj.cinemaandroidapp.R
import ru.sedooj.cinemaandroidapp.navigation.Screens
import ru.sedooj.cinemaandroidapp.ui.design.pages.CenteredScreenContentComponent

@Composable
fun ChoosePositionPage(
    modifier: Modifier,
    padding: PaddingValues,
    navController: NavController
) {

    val selectedSeance =
        SchedulePageState()

    CenteredScreenContentComponent(
        modifier = Modifier,
        mainPaddingValue = PaddingValues(start = 10.dp, end = 10.dp),
        title = Screens.POSITION.pageName,
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Back",
                    )
                })
        },
        content = {
            selectedSeance._selectedSeanceState.value?.let {
                PositionsChooseComponent(
                    positions = it.places,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    )


}

private class Place(
    var row: Int = -1,
    var column: Int = -1,
    var price: Int = -1,
    var type: String = "NOT_SET"
)

@Composable
private fun PositionsChooseComponent(
    positions: List<List<SelectedHallTimeState.Place>>,
    modifier: Modifier,
) {
    val scrollState = rememberScrollState()
    val rowsList = remember {
        mutableStateOf(positions)
    }

    val selectedSeats = remember { mutableListOf<Place>() }

    Column(
        modifier = modifier.verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SeatsMatrixComponent(
            places = rowsList,
            modifier = modifier,
            onSeatSelect = { place ->
                selectedSeats.add(
                    Place(
                        column = place.column,
                        row = place.row,
                        price = place.price,
                        type = place.type
                    )
                )
            }
        )
        DataWithContinueButtonComponent(
            modifier = modifier,
            onContinue = {

            },
            selectedSeatsState = selectedSeats

        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SeatsMatrixComponent(
    onSeatSelect: (Place) -> Unit,
    places: MutableState<List<List<SelectedHallTimeState.Place>>>,
    modifier: Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Экран", textAlign = TextAlign.Center)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .border(2.dp, shape = RoundedCornerShape(5.dp), color = Color.LightGray)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp))
                )
            }
            Text(
                "Ряд",
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
            places.value.forEachIndexed { i, row ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "${i + 1}",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                    FlowRow(
                        modifier = Modifier.fillMaxSize(),
                        maxItemsInEachRow = places.component1().lastIndex + 1,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        row.forEachIndexed { j, column ->
                            Button(
                                onClick = {
                                    onSeatSelect(
                                        Place(
                                            row = i,
                                            column = j,
                                            price = column.price,
                                            type = column.type
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                            ) {
                                Text(
                                    "${j + 1}",
                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DataWithContinueButtonComponent(
    modifier: Modifier,
    onContinue: () -> Unit,
    selectedSeatsState: MutableList<Place>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val totalPrice = remember { mutableIntStateOf(0) }
        Text(
            text = "Места",
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        )
        FlowColumn(
            maxItemsInEachColumn = 3,
            modifier = Modifier.fillMaxSize()
        ) {
            selectedSeatsState.forEach {
                Text(
                    text = "${it.row} ряд - ${it.column} место.",
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                )
                totalPrice.intValue += it.price
            }
        }
        Text(
            text = "Итого: ${totalPrice.intValue}₽",
            fontSize = MaterialTheme.typography.labelMedium.fontSize,
        )
        Button(
            onClick = {
                onContinue()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "К оплате",
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PositionChooserComponent(
    title: String,
    positions: List<String>,
    onChoose: (Int) -> Unit,
    modifier: Modifier,
    data: Int
) {
    val sheetState = rememberModalBottomSheetState()
    val sheetVisible = remember { mutableStateOf(false) }

    PositionChooserDisplayDataComponent(
        modifier = modifier,
        title = title,
        data = data,
        onClick = {
            sheetVisible.value = true
        }
    )

    RowBottomSheetComponent(
        onSelect = {
            onChoose(it)
            sheetVisible.value = false
        },
        onDismiss = {
            sheetVisible.value = false
        },
        sheetState = sheetState,
        sheetVisible = sheetVisible.value,
        selectedElement = data
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PositionChooserDisplayDataComponent(
    onClick: () -> Unit,
    modifier: Modifier,
    title: String,
    data: Int
) {
    Card(
        modifier = modifier.height(80.dp),
        onClick = {
            onClick()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(12f),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "$title:",
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = if (data == 0) "Нажмите, чтобы выбрать" else "$data",
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            textAlign = TextAlign.Start
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Icon(
                        painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Expand icon",
                        modifier = Modifier.rotate(-90f)
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RowBottomSheetComponent(
    onSelect: (Int) -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    sheetVisible: Boolean,
    selectedElement: Int
) {

    val scrollState = rememberScrollState()

    if (sheetVisible)
        ModalBottomSheet(
            onDismissRequest = {
                onDismiss()
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(state = scrollState)
            ) {
                List(11) {
                    Button(onClick = { onSelect(it) }, enabled = selectedElement != it) {
                        Text(text = "$it")
                    }
                }
            }
        }
}