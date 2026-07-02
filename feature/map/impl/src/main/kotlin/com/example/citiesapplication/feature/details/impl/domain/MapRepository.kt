package com.example.citiesapplication.feature.details.impl.domain

import com.example.citiesapplication.core.network.model.NetworkResult
import com.example.citiesapplication.feature.details.impl.domain.model.City
import kotlinx.coroutines.flow.Flow

internal interface MapRepository {
    fun getCitiesForMap(
        centerLat: Double,
        centerLng: Double,
        radius: Int
    ): Flow<NetworkResult<List<City>>>
}