package ru.sedooj.cinemaandroidapp.pages.poster.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.sedooj.cinemaandroidapp.R
import ru.sedooj.cinemaandroidapp.navigation.Screens
import ru.sedooj.cinemaandroidapp.ui.design.pages.CenteredScreenContentComponent


data class CardDetails(
    val pan: String,
    val expireDate: String,
    val cvv: String
)

@Composable
fun CardDetailsPage(
    modifier: Modifier,
    paddingValues: PaddingValues,
    navController: NavController
) {
    val cardDetails = remember {
        mutableStateOf(
            CardDetails(
                pan = "", expireDate = "", cvv = ""
            )
        )
    }

    CenteredScreenContentComponent(
        title = Screens.CARD_DETAILS.pageName,
        modifier = modifier,
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
        mainPaddingValue = paddingValues
    ) {
        CardDetailsComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            onPay = {
                /*TODO: Оплата*/
            },
            cardDetails = cardDetails.value,
            onPan = {
                cardDetails.value = cardDetails.value.copy(pan = it)
            },
            onExpireDate = {
                cardDetails.value = cardDetails.value.copy(expireDate = it)
            },
            onCVV = {
                cardDetails.value = cardDetails.value.copy(cvv = it)
            }
        )
    }

}

@Composable
private fun CardDetailsComponent(
    modifier: Modifier,
    cardDetails: CardDetails,
    onPan: (String) -> Unit,
    onExpireDate: (String) -> Unit,
    onCVV: (String) -> Unit,
    onPay: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .wrapContentHeight()
                .weight(2f)
        ) {
            CardDetailsInputsComponent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp),
                onPan = {
                    onPan(it)
                },
                onExpireDate = {
                    onExpireDate(it)
                },
                onCVV = {
                    onCVV(it)
                },
                cardDetails = cardDetails
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f),
            contentAlignment = Alignment.TopStart
        ) {
            Button(
                onClick = {
                    onPay()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Оплатить",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.inversePrimary
                )
            }

        }
    }
}

@Composable
private fun CardDetailsInputsComponent(
    modifier: Modifier,
    cardDetails: CardDetails,
    onPan: (String) -> Unit,
    onExpireDate: (String) -> Unit,
    onCVV: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = "Номер карты*",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
                TextField(
                    label = {
                        Text(text = "1234 5678")
                    },
                    value = cardDetails.pan,
                    onValueChange = {
                        onPan(it)
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = cardDetails.pan.length > 8
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Срок действия*",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                    TextField(
                        value = cardDetails.expireDate,
                        onValueChange = {
                            onExpireDate(it)
                        },
                        singleLine = true,
                        label = {
                            Text(text = "12/34")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = cardDetails.expireDate.length > 4
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "CVV*",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                    TextField(
                        value = cardDetails.cvv,
                        onValueChange = {
                            onCVV(it)
                        },
                        singleLine = true,
                        label = {
                            Text(text = "123")
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = cardDetails.cvv.length > 3
                    )
                }
            }

        }
    }
}