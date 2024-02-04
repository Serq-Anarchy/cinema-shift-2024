package ru.sedooj.cinemaandroidapp.ui.design.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalListCardComponent(
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
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            shape = RoundedCornerShape(
                                10.dp
                            )
                        )
                        .size(
                            width = 150.dp,
                            height = 150.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            if (icon != null) {
                                Box(
                                    modifier = Modifier
                                        .size(
                                            width = 100.dp,
                                            height = 100.dp
                                        )
                                        .background(
                                            color = MaterialTheme.colorScheme.surface,
                                            shape = RoundedCornerShape(
                                                10.dp
                                            )
                                        )
                                        .weight(2f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = icon,
                                        contentDescription = "Icon",
                                        modifier = Modifier.size(
                                            width = 50.dp,
                                            height = 50.dp
                                        ),
                                        tint = MaterialTheme.colorScheme.inverseSurface
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item.capitalize(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}