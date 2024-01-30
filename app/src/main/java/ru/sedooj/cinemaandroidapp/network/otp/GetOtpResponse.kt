package ru.sedooj.cinemaandroidapp.network.otp

import kotlinx.serialization.Serializable

@Serializable
data class GetOtpResponse (
    val success : Boolean,
    val retryDelay : Long
)