package com.example.citiesapplication.feature.details.impl.presentation.data

import com.example.citiesapplication.feature.details.impl.domain.model.City

internal data class MapState(
    val cities: List<City> = emptyList(),
    val checkedCity: City? = null,
    val isLoading: Boolean = true,
    val centerMoved: Boolean = false,
    val error: String? = null,
)
