package com.example.citiesapplication.feature.details.impl.presentation.data

import com.example.citiesapplication.feature.details.impl.domain.model.City

internal sealed interface MapAction {
    data object LoadInitialData : MapAction
    data class OnCameraChanged(
        val zoomLevel: Double,
        val centerLat: Double,
        val centerLng: Double,
        val radius: Int
    ) : MapAction
    data class OnCityClick(val city: City) : MapAction
    data object OnBottomSheetDismiss : MapAction  // ← НОВОЕ
    data object OnOpenInBrowser : MapAction
}