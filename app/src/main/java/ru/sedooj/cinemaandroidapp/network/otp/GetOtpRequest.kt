package ru.sedooj.cinemaandroidapp.network.otp

import kotlinx.serialization.Serializable

@Serializable
data class GetOtpRequest(
    val phone: String
)
