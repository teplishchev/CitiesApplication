package com.example.citiesapplication.feature.citieslist.impl.list.presentation.model

import com.example.citiesapplication.core.network.model.NetworkException
import com.example.citiesapplication.feature.citieslist.impl.list.domain.model.City

internal data class CitiesListState(
    val query: String = "",
    val cities: List<City> = emptyList(),
    val currentPage: Int = 1,
    val isLoading: Boolean = true,
    val isLoadingMore: Boolean = false,
    val error: NetworkException? = null,
    val canLoadMore: Boolean = true,
) {
    val isEmpty: Boolean
        get() = cities.isEmpty() && !isLoading && !isLoadingMore && error == null
}