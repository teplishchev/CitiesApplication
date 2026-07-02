package com.example.citiesapplication.feature.citieslist.impl.list.presentation.model

import com.example.citiesapplication.feature.citieslist.impl.list.domain.model.City

internal sealed interface CitiesListSideEffect {

    data class ShowErrorSnackbar(val errorMessage: String) : CitiesListSideEffect

    data class NavigateToDetailsScreen(val city: City) : CitiesListSideEffect
}