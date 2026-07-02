package com.example.citiesapplication.feature.citieslist.impl.list.domain.model

internal data class City(
    val id: Long,
    val name: String,
    val country: String,
    val pop: Long,
)