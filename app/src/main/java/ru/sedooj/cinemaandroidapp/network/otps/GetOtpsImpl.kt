package ru.sedooj.cinemaandroidapp.network.otps

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class GetOtpsImpl(
    private val client: HttpClient
) : GetOtps {

    override suspend fun getOtpsCode(getOtpRequest: GetOtpRequest): GetOtpsResponse? {
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