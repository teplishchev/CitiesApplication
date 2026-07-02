package com.example.citiesapplication.feature.citieslist.impl.list.domain.model

internal data class CitiesListPage(
    val cities: List<City>,
    val limit: Int,
    val page: Int,
    val total: Int,
)