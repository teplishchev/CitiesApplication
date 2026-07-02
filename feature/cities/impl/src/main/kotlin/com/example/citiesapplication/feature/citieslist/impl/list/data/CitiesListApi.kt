package com.example.citiesapplication.feature.citieslist.impl.list.data

import com.example.citiesapplication.feature.citieslist.impl.list.data.dto.CitiesListResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

internal class CitiesListApi(
    private val client: HttpClient,
) {

    suspend fun getCitiesList(query: String, page: Int, limit: Int): CitiesListResponseDto =
        client.get("api/cities") {
            header("Accept-Language", "ru")
            parameter("query", query)
            parameter("page", page)
            parameter("limit", limit)
        }.body()
}