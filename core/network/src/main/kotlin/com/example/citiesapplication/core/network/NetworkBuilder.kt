package com.example.citiesapplication.core.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkBuilder {

    private const val HOST = "dev-dep.tools.urent.tech"
    private const val PORT = 8080
    private const val LOG_TAG = "NETWORK"

    fun buildClient(): HttpClient = HttpClient(OkHttp) {
        expectSuccess = true

        install(HttpTimeout) {
            requestTimeoutMillis = 20000
            connectTimeoutMillis = 15000
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Logging) {
            level = LogLevel.INFO
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d(LOG_TAG, message)
                }
            }
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTP
                host = HOST
                port = PORT
            }
        }
    }
}