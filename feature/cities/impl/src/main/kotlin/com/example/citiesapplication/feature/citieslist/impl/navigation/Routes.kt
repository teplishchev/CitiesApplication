package com.example.citiesapplication.feature.citieslist.impl.navigation

import kotlinx.serialization.Serializable

@Serializable
object CitiesListRoute

@Serializable
data class CityDetailRoute(
    val name: String,
    val country: String,
    val population: Long
)