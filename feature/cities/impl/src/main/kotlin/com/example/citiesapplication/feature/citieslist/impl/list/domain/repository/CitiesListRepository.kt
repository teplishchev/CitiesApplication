package com.example.citiesapplication.feature.citieslist.impl.list.domain.repository

import com.example.citiesapplication.core.network.model.NetworkResult
import com.example.citiesapplication.feature.citieslist.impl.list.domain.model.CitiesListPage
import kotlinx.coroutines.flow.Flow

internal interface CitiesListRepository {

    suspend fun getCitiesList(
        query: String,
        page: Int,
        limit: Int
    ): Flow<NetworkResult<CitiesListPage>>
}