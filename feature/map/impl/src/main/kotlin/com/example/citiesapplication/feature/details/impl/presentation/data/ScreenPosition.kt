package com.example.citiesapplication.feature.details.impl.presentation.data

import com.example.citiesapplication.feature.details.impl.domain.model.City

internal data class ScreenPosition(
    val city: City,
    val x: Float,
    val y: Float
)