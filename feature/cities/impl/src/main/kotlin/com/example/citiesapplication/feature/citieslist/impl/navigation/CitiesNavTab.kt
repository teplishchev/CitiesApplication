package com.example.citiesapplication.feature.citieslist.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.citiesapplication.core.navigation.bottomnavigation.BottomTabs
import com.example.citiesapplication.core.navigation.bottomnavigation.TabNavigator
import com.example.citiesapplication.feature.citieslist.impl.details.presentation.ui.DetailsScreen
import com.example.citiesapplication.feature.citieslist.impl.list.presentation.ui.CitiesListScreen

@Composable
fun CitiesNavTab(
    tabNavigator: TabNavigator
) {
    val navController = rememberNavController()

    DisposableEffect(navController) {
        tabNavigator.registerNavController(BottomTabs.CITIES, navController)
        onDispose {
            tabNavigator.unregisterNavController(BottomTabs.CITIES)
        }
    }

    NavHost(
        navController = navController,
        startDestination = CitiesListRoute
    ) {
        composable<CitiesListRoute> {
            CitiesListScreen(
                onNavigateToDetails = { city ->
                    navController.navigate(
                        CityDetailRoute(
                            name = city.name,
                            population = city.pop,
                            country = city.country
                        )
                    )
                }
            )
        }

        composable<CityDetailRoute> {
            DetailsScreen(
                onBack = navController::popBackStack
            )
        }
    }
}