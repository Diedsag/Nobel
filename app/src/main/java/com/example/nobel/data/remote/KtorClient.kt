package com.example.nobel.data.remote

import android.net.http.HttpResponseCache.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient{

    private var currentAccessToken: String? = null
    val client = HttpClient(Android) {

        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 15000
        }

        install("TokenInterceptor") {
            requestPipeline.intercept(HttpRequestPipeline.State) {
                currentAccessToken?.let { token ->
                    context.header("Authorization", "Bearer $token")
                }
            }
        }

        install(Auth) {
            bearer {
                sendWithoutRequest { true }
                loadTokens {
                    currentAccessToken?.let { BearerTokens(it, "") }
                }
            }
        }

        defaultRequest {
            url("https://api.nobelprize.org/2.1/")
        }

    }

}