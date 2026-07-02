package com.example.citiesapplication.feature.citieslist.impl.details.presentation.model

internal sealed interface DetailSideEffect {

    data object Back : DetailSideEffect

    data class OpenWebSearch(val url: String) : DetailSideEffect
}