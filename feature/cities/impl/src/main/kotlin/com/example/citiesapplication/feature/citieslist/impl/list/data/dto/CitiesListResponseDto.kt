package com.example.citiesapplication.feature.citieslist.impl.list.data.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class CitiesListResponseDto(
    val items: List<CityDto> = emptyList(),
    val page: Int = 1,
    val limit: Int = 0,
    val total: Int = 0,
)
