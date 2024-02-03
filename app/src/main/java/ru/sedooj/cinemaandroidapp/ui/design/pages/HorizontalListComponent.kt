package ru.sedooj.cinemaandroidapp.ui.design.pages

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalListCardComponent(
    onClick:() -> Unit,
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
                        onClick()
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