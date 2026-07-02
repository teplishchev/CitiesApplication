package com.example.citiesapplication.feature.citieslist.api

import kotlinx.serialization.Serializable

@Serializable
data class CityInfo(
    val id: Long,
    val name: String,
    val country: String,
    val pop: Long = 0L
)