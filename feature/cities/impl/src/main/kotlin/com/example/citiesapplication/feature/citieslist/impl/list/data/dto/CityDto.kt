package com.example.citiesapplication.feature.citieslist.impl.list.data.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class CityDto(
    val id: Long,
    val name: String,
    val country: String,
    val pop: Long = 0L,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
)
