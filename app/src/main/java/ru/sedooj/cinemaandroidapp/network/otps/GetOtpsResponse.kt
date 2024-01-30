package ru.sedooj.cinemaandroidapp.network.otps

import kotlinx.serialization.Serializable

@Serializable
data class GetOtpsResponse (
    val success : Boolean,
    val retryDelay : Long
)