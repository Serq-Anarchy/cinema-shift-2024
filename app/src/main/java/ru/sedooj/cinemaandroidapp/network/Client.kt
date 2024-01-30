package ru.sedooj.cinemaandroidapp.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import ru.sedooj.cinemaandroidapp.network.otps.GetOtpsImpl

interface Client {
    val defaultURL : String
        get() = "https://shift-backend.onrender.com"

    companion object {
        fun create(): HttpClient {
            return HttpClient(Android) {
                    install(Logging) {
                        class ClientLogger : Logger {
                            override fun log(message: String) {
                                Log.d("ClientLogger", message)
                            }
                        }
                        val clientLogger = ClientLogger()
                        logger = clientLogger
                    }
                    install(ContentNegotiation) {
                        json()
                    }
                }

        }
    }

}