package com.example.citiesapplication.feature.citieslist.impl.navigation

import androidx.navigation.NavController
import com.example.citiesapplication.feature.citieslist.api.CitiesRouter
import com.example.citiesapplication.feature.citieslist.api.CityInfo

class CitiesRouterImpl(
    private val navController: NavController
) : CitiesRouter {

    override fun navigateToCityDetails(cityInfo: CityInfo) {
        navController.navigate(CityDetailRoute(cityInfo.name, cityInfo.country, cityInfo.pop)) {
            launchSingleTop = true
        }
    }

    override fun goBack() {
        navController.popBackStack()
    }
}