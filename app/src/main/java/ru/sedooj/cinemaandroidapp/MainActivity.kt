package ru.sedooj.cinemaandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.sedooj.cinemaandroidapp.pages.MainScreen
import ru.sedooj.cinemaandroidapp.ui.theme.CinemaAndroidAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CinemaAndroidAppTheme {
                MainScreen()
            }
        }
    }
}