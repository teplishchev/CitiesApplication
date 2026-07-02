package com.example.citiesapplication.feature.details.impl.data.repository

import com.example.citiesapplication.core.network.ConnectivityMonitor
import com.example.citiesapplication.core.network.model.NetworkResult
import com.example.citiesapplication.core.network.utils.safeApiCall
import com.example.citiesapplication.feature.details.impl.data.MapApi
import com.example.citiesapplication.feature.details.impl.data.mapper.toDomain
import com.example.citiesapplication.feature.details.impl.domain.MapRepository
import com.example.citiesapplication.feature.details.impl.domain.model.City
import kotlinx.coroutines.flow.Flow

internal class MapRepositoryImpl(
    private val api: MapApi,
    private val connectivityMonitor: ConnectivityMonitor
): MapRepository {
    override fun getCitiesForMap(
        centerLat: Double,
        centerLng: Double,
        radius: Int
    ): Flow<NetworkResult<List<City>>> =
        safeApiCall(connectivityMonitor) {
            api.getCitiesMap(
                centerLat = centerLat,
                centerLng = centerLng,
                radius = radius
            ).items.map { it.toDomain() }
        }
}