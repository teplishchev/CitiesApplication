package com.example.citiesapplication.feature.citieslist.impl.list.presentation.model

sealed interface CitiesListAction {
    data object LoadCitiesList : CitiesListAction
    data object LoadNextPage : CitiesListAction
    data class OnSearchQueryChanged(val query: String): CitiesListAction
}