package com.example.citiesapplication.feature.details.impl.data

import com.example.citiesapplication.feature.details.impl.data.dto.GetCitiesFromMapResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

internal class MapApi(
    private val client: HttpClient,
) {

    suspend fun getCitiesMap(
        centerLat: Double,
        centerLng: Double,
        radius: Int
    ): GetCitiesFromMapResponse =
        client.get("api/cities/map") {
            header("Accept-Language", "ru")
            parameter("centerLat", centerLat)
            parameter("centerLng", centerLng)
            parameter("radius", radius)
        }.body()
}