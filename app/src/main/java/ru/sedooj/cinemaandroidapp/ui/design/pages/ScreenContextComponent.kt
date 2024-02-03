package ru.sedooj.cinemaandroidapp.ui.design.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollableCenteredScreenContentComponent(
    modifier: Modifier,
    title: String = "Title",
    navigationIcon: @Composable () -> Unit,
    mainPaddingValue: PaddingValues,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    content: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier.padding(mainPaddingValue),
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize
                    )
                },
                navigationIcon = {
                    navigationIcon()
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        floatingActionButton = {
            floatingActionButton()
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(state = scrollState),
                        verticalArrangement = Arrangement.spacedBy(
                            15.dp,
                            alignment = Alignment.Top
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            Spacer(modifier = Modifier.height(0.dp))
                            content()
                        }
                    )
                }
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredScreenContentComponent(
    modifier: Modifier,
    title: String = "Title",
    navigationIcon: @Composable () -> Unit,
    mainPaddingValue: PaddingValues,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    actions: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier.padding(mainPaddingValue),
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize
                    )
                },
                navigationIcon = {
                    navigationIcon()
                },
                actions = {
                          actions()
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        floatingActionButton = {
            floatingActionButton()
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(
                            15.dp,
                            alignment = Alignment.Top
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            Spacer(modifier = Modifier.height(0.dp))
                            content()
                        }
                    )
                }
            )
        }
    )
}