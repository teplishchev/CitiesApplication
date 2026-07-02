package com.example.citiesapplication.core.navigation.bottomnavigation

import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow

enum class BottomTabs(val route: String) {
    CITIES("cities_tab"),
    MAP("map_tab")
}

interface TabNavigator {
    val currentTabFlow: StateFlow<BottomTabs>
    val canGoBack: StateFlow<Boolean>
    fun selectTab(tab: BottomTabs)
    fun goBack(): Boolean
    fun registerNavController(tab: BottomTabs, controller: NavController)
    fun unregisterNavController(tab: BottomTabs)
}