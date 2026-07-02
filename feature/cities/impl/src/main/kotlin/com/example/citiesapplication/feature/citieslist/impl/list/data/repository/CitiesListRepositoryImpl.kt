package com.example.citiesapplication.feature.citieslist.impl.list.data.repository

import com.example.citiesapplication.core.network.ConnectivityMonitor
import com.example.citiesapplication.core.network.model.NetworkResult
import com.example.citiesapplication.core.network.utils.safeApiCall
import com.example.citiesapplication.feature.citieslist.impl.list.data.CitiesListApi
import com.example.citiesapplication.feature.citieslist.impl.list.data.mapper.toDomain
import com.example.citiesapplication.feature.citieslist.impl.list.domain.model.CitiesListPage
import com.example.citiesapplication.feature.citieslist.impl.list.domain.repository.CitiesListRepository
import kotlinx.coroutines.flow.Flow

internal class CitiesListRepositoryImpl(
    private val api: CitiesListApi,
    private val connectivityMonitor: ConnectivityMonitor,
) : CitiesListRepository {

    override suspend fun getCitiesList(
        query: String,
        page: Int, limit: Int
    ): Flow<NetworkResult<CitiesListPage>> =
        safeApiCall(connectivityMonitor) {
            api.getCitiesList(
                query = query,
                page = page,
                limit = limit
            ).toDomain()
        }
}