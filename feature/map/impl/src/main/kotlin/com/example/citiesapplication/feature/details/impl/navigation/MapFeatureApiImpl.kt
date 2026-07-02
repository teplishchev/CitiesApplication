package com.example.citiesapplication.feature.details.impl.navigation

import androidx.navigation.NavController
import com.example.citiesapplication.core.navigation.bottomnavigation.TabContent
import com.example.citiesapplication.core.navigation.bottomnavigation.TabNavigator
import com.example.citiesapplication.core.ui.composable.ComposeTabContent
import com.example.citiesapplication.feature.details.api.MapFeatureApi
import com.example.citiesapplication.feature.details.api.MapRouter

internal class MapFeatureApiImpl(
    private val tabNavigator: TabNavigator
): MapFeatureApi {

    override fun mapTab(): TabContent {
        return ComposeTabContent {
            MapNavTab(tabNavigator)
        }
    }

    override fun getMapRouter(navController: NavController): MapRouter {
        return MapRouterImpl(navController)
    }

}