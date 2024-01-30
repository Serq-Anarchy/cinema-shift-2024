package ru.sedooj.cinemaandroidapp.network.otps

import kotlinx.serialization.Serializable

@Serializable
data class GetOtpRequest(
    val phone: String
)
