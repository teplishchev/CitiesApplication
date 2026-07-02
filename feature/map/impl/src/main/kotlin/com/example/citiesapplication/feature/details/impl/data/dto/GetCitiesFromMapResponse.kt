package com.example.citiesapplication.feature.details.impl.data.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class GetCitiesFromMapResponse(
    val items: List<CityDto> = emptyList(),
)
