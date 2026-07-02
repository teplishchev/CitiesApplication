package com.example.citiesapplication.feature.details.api

import androidx.navigation.NavController
import com.example.citiesapplication.core.navigation.bottomnavigation.TabContent

interface MapFeatureApi {
    fun mapTab(): TabContent
    fun getMapRouter(navController: NavController): MapRouter
}