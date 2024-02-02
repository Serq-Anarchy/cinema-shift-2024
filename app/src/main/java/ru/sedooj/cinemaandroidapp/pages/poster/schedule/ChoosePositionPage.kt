package ru.sedooj.cinemaandroidapp.pages.poster.schedule

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
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.sedooj.cinemaandroidapp.R
import ru.sedooj.cinemaandroidapp.navigation.Screens
import ru.sedooj.cinemaandroidapp.ui.design.pages.ScrollableCenteredScreenContentComponent

@Composable
fun ChoosePositionPage(
    modifier: Modifier,
    padding: PaddingValues,
    navController: NavController
) {

    val selectedSeance =
        SchedulePageState()

    ScrollableCenteredScreenContentComponent(
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
            selectedSeance._selectedSeanceState.value?.let { PositionsChooseComponent(positions = it.places) }
        }
    )


}

private class Place(
    val row: Int = 0,
    val column: Int = 0,
    val price: Int = 0,
    val type: String = "Unspecified"
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PositionsChooseComponent(
    positions: List<List<SelectedHallTimeState.Place>>
) {
    val rowsList = remember {
        mutableStateOf(positions)
    }

    val places = remember { mutableListOf(Place()) }

    FlowColumn(
        maxItemsInEachColumn = 3,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        rowsList.value.forEachIndexed { i, row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Ряд ${i+1}",
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
                row.forEachIndexed { j, column ->
//                Box(
//                    modifier = Modifier
//                        .wrapContentSize()
//                        .clip(RoundedCornerShape(5.dp)), contentAlignment = Alignment.Center
//                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Место ${j+1}",
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                        Button(onClick = { /*TODO*/ }) {
//                        Text("R: $i C: $j")
                        }
                    }

//                }


                }
            }

        }
    }


    //            places.add(
//                element = Place(
//                    row = i,
//                    column = j,
//                    price = column.price,
//                    type = column.type
//                )
//            )

//    val row = remember { mutableIntStateOf(0) }
//
//    PositionChooserComponent(
//        title = "Ряд",
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(),
//        data = row.intValue,
//        onChoose = {
//            row.intValue = it
//        },
//        positions = selectedSeance._selectedSeanceState.value.places
//    )
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