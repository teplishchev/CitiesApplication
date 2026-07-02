package com.example.citiesapplication.feature.citieslist.api

import androidx.navigation.NavController
import com.example.citiesapplication.core.navigation.bottomnavigation.TabContent
import kotlinx.serialization.Serializable

interface CitiesFeatureApi {

    fun citiesTab(): TabContent
    fun getCitiesRouter(navController: NavController): CitiesRouter
}