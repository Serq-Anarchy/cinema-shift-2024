package ru.sedooj.cinemaandroidapp.ui.design.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScrollableCenteredScreenContentComponent(
    modifier: Modifier,
    title: String = "Title",
    mainPaddingValue: PaddingValues,
    content: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier.padding(mainPaddingValue),
        topBar = {
            TopAppBarText(
                title = title
            )
        },
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