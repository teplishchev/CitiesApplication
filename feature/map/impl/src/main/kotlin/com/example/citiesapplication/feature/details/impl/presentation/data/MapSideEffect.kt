package com.example.citiesapplication.feature.details.impl.presentation.data

internal sealed interface MapSideEffect {

    data class OpenInBrowser(val url: String) : MapSideEffect
}