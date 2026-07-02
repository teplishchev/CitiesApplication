package com.example.citiesapplication.core.navigation.bottomnavigation

import android.util.Log
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//class TabNavigatorImpl : TabNavigator {
//    private val _currentTab = MutableStateFlow(BottomTabs.CITIES)
//    override val currentTabFlow: StateFlow<BottomTabs> = _currentTab.asStateFlow()
//
//    override fun selectTab(tab: BottomTabs) {
//        _currentTab.value = tab
//    }
//
//    override fun getCurrentTab(): BottomTabs {
//        return _currentTab.value
//    }
//}

class TabNavigatorImpl(
    private val initialTab: BottomTabs = BottomTabs.CITIES
) : TabNavigator {

    private val backStack = ArrayDeque<BottomTabs>().apply {
        addLast(initialTab)
    }

    private val _currentTab = MutableStateFlow(initialTab)
    override val currentTabFlow: StateFlow<BottomTabs> = _currentTab.asStateFlow()

    private val _canGoBack = MutableStateFlow(false)
    override val canGoBack: StateFlow<Boolean> = _canGoBack.asStateFlow()

    // NavController для каждого таба
    private val navControllers = mutableMapOf<BottomTabs, NavController>()

    override fun registerNavController(tab: BottomTabs, controller: NavController) {
        Log.d("BACK_DEBUG", "registerNavController: tab=$tab")
        navControllers[tab] = controller

        // Слушаем изменения back stack NavController
        controller.addOnDestinationChangedListener { _, _, _ ->
            Log.d("BACK_DEBUG", "NavController destination changed for tab=$tab")
            updateCanGoBack()
        }

        updateCanGoBack() // ← ОБЯЗАТЕЛЬНО!
    }

    override fun unregisterNavController(tab: BottomTabs) {
        Log.d("BACK_DEBUG", "unregisterNavController: tab=$tab")
        navControllers.remove(tab)
        updateCanGoBack()
    }

    private fun updateCanGoBack() {
        val currentTab = _currentTab.value
        val controller = navControllers[currentTab]
        val hasInternalBack = controller?.previousBackStackEntry != null
        val hasTabBack = backStack.size > 1
        val newValue = hasInternalBack || hasTabBack

        _canGoBack.value = newValue

        Log.d("BACK_DEBUG", "updateCanGoBack: $newValue " +
                "(hasInternalBack=$hasInternalBack, hasTabBack=$hasTabBack, " +
                "backStack.size=${backStack.size}, controller=$controller)")
    }

    override fun goBack(): Boolean {
        Log.d("BACK_DEBUG", "goBack called")

        val currentTab = _currentTab.value
        val controller = navControllers[currentTab]

        // 1. Сначала пробуем вернуться внутри таба
        if (controller?.previousBackStackEntry != null) {
            Log.d("BACK_DEBUG", "goBack: pop inside tab $currentTab")
            val result = controller.popBackStack()
            updateCanGoBack()
            return result
        }

        // 2. Если внутри таба некуда — переключаем таб
        if (backStack.size <= 1) {
            Log.d("BACK_DEBUG", "goBack: nothing to go back to")
            return false
        }

        backStack.removeLast()
        val previousTab = backStack.last()
        _currentTab.value = previousTab
        Log.d("BACK_DEBUG", "goBack: switched to $previousTab")
        updateCanGoBack()
        return true
    }

    override fun selectTab(tab: BottomTabs) {
        Log.d("BACK_DEBUG", "selectTab: $tab, current=${_currentTab.value}")

        if (_currentTab.value == tab) {
            Log.d("BACK_DEBUG", "selectTab: same tab, skip")
            return
        }

        backStack.remove(tab)
        backStack.addLast(tab)
        _currentTab.value = tab

        Log.d("BACK_DEBUG", "selectTab: backStack=$backStack")
        updateCanGoBack() // ← ОБЯЗАТЕЛЬНО!
    }
}