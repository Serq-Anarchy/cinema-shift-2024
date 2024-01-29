package ru.sedooj.cinemaandroidapp.ui.design.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBarText(title: String) {
    Text(
        title, modifier = Modifier
            .fillMaxWidth()
            .height(41.dp)
            .padding(top = 13.dp),
        textAlign = TextAlign.Center,
        fontSize = 25.sp
    )
}