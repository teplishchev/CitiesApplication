package com.example.citiesapplication.feature.details.impl.domain.model

internal data class City(
    val id: Long,
    val name: String,
    val country: String,
    val pop: Long,
    val lat: Double,
    val lng: Double
)
