package com.example.citiesapplication.feature.citieslist.impl

import androidx.navigation.NavController
import com.example.citiesapplication.core.navigation.bottomnavigation.TabContent
import com.example.citiesapplication.core.navigation.bottomnavigation.TabNavigator
import com.example.citiesapplication.core.ui.composable.ComposeTabContent
import com.example.citiesapplication.feature.citieslist.api.CitiesFeatureApi
import com.example.citiesapplication.feature.citieslist.api.CitiesRouter
import com.example.citiesapplication.feature.citieslist.impl.navigation.CitiesNavTab
import com.example.citiesapplication.feature.citieslist.impl.navigation.CitiesRouterImpl

class CitiesFeatureApiImpl(
    private val tabNavigator: TabNavigator
): CitiesFeatureApi {

    override fun citiesTab(): TabContent {
        return ComposeTabContent {
            CitiesNavTab(tabNavigator)
        }
    }

    override fun getCitiesRouter(navController: NavController): CitiesRouter {
        return CitiesRouterImpl(navController)
    }
}