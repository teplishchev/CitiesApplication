package com.example.citiesapplication.feature.details.impl.navigation

import androidx.navigation.NavController
import com.example.citiesapplication.feature.details.api.MapRouter

class MapRouterImpl(
    private val navController: NavController
) : MapRouter {

    override fun goBack() {
        navController.popBackStack()
    }
}