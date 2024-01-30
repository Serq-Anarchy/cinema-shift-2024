package ru.sedooj.cinemaandroidapp.network.otp

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.sedooj.cinemaandroidapp.network.Client

class GetOtpImpl(
    private val client : HttpClient
) : GetOtp {

    override suspend fun getOtpCode(getOtpRequest: GetOtpRequest): GetOtpResponse? {
        return client.post("$defaultURL/auth/otp/") {
            contentType(ContentType.Application.Json)
            setBody(
                GetOtpRequest(
                phone = getOtpRequest.phone
            )
            )
        }.body()
    }
}