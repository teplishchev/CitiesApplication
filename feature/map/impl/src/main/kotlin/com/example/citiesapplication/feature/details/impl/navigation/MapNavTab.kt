package com.example.citiesapplication.feature.details.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.citiesapplication.core.navigation.bottomnavigation.BottomTabs
import com.example.citiesapplication.core.navigation.bottomnavigation.TabNavigator
import com.example.citiesapplication.feature.details.impl.presentation.ui.MapScreen

@Composable
fun MapNavTab(
    tabNavigator: TabNavigator
) {
    val navController = rememberNavController()

    DisposableEffect(navController) {
        tabNavigator.registerNavController(BottomTabs.MAP, navController)
        onDispose {
            tabNavigator.unregisterNavController(BottomTabs.MAP)
        }
    }

    NavHost(
        navController = navController,
        startDestination = MapRoute
    ) {
        composable<MapRoute> {
            MapScreen()
        }
    }
}