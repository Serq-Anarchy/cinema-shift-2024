package ru.sedooj.cinemaandroidapp.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object Data {

    const val BASE_URL: String = "https://shift-backend.onrender.com"

}

interface Client {

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
                        json(Json {
                            this.coerceInputValues = true
                        })
                    }
                }

        }
    }

}