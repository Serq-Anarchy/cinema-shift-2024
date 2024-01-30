package ru.sedooj.cinemaandroidapp.network.otp

import ru.sedooj.cinemaandroidapp.network.Client

interface GetOtp : Client {

    suspend fun getOtpCode(getOtpRequest: GetOtpRequest): GetOtpResponse?

}