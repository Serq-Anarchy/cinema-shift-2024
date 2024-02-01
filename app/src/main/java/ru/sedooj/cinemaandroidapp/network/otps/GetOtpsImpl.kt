package ru.sedooj.cinemaandroidapp.network.otps

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.sedooj.cinemaandroidapp.network.Data

class GetOtpsImpl(
    private val client: HttpClient
) : GetOtps {

    override suspend fun getOtpsCode(getOtpInput: GetOtpInput): GetOtpsResponse? {
        return client.post("${Data.BASE_URL}/auth/otp/") {
            contentType(ContentType.Application.Json)
            setBody(
                GetOtpInput(
                    phone = getOtpInput.phone
                )
            )
        }.body()
    }
}