package ru.sedooj.cinemaandroidapp.pages.poster.schedule

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.sedooj.cinemaandroidapp.R
import ru.sedooj.cinemaandroidapp.navigation.Screens
import ru.sedooj.cinemaandroidapp.ui.design.pages.ScrollableCenteredScreenContentComponent

@OptIn(ExperimentalMaterial3Api::class)
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
        mainPaddingValue = padding,
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
            PositionChooser(title = "Ряд", modifier = Modifier.fillMaxWidth())

        }
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PositionChooser(
    title: String,
//    positions: List<String>,
    modifier: Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    val sheetVisible = remember { mutableStateOf(false) }
    val data = remember { mutableIntStateOf(0) }

    Card(
        modifier = modifier,
        onClick = {
            sheetVisible.value = true
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "$title:",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
            Text(
                text = if (data.intValue == 0) "Нажмите, чтобы выбрать" else "$data",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
        }
    }

    RowBottomSheetComponent(
        onSelect = {
            sheetVisible.value = false
        },
        onDismiss = {
            sheetVisible.value = false
        },
        sheetState = sheetState,
        sheetVisible = sheetVisible.value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RowBottomSheetComponent(
    onSelect: (Int) -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    sheetVisible: Boolean
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
                    Button(onClick = { onSelect(it) }) {
                        Text(text = "$it")
                    }
                }
            }
        }
}