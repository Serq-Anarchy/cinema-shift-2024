package ru.sedooj.cinemaandroidapp.ui.design.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NavigationIconComponent(
    navController : NavController,
    icon : Painter,
    description : String = ""
) {
    IconButton(
        onClick = { navController.popBackStack() },
        content = {
            Icon(
                painter = icon,
                contentDescription = description,
                tint = Color.LightGray
            )
        },
        modifier = Modifier.padding(end = 20.dp)
    )
}