package com.example.citiesapplication.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.citiesapplication.R
import com.example.citiesapplication.core.navigation.bottomnavigation.BottomTabs
import com.example.citiesapplication.core.navigation.bottomnavigation.TabNavigator
import com.example.citiesapplication.core.ui.composable.ComposeTabContent
import com.example.citiesapplication.core.ui.theme.AppTheme
import com.example.citiesapplication.feature.citieslist.api.CitiesFeatureApi
import com.example.citiesapplication.feature.details.api.MapFeatureApi
import org.koin.compose.koinInject

@Composable
fun AppNavigation(
    citiesApi: CitiesFeatureApi = koinInject(),
    mapApi: MapFeatureApi = koinInject()
) {
    val tabNavigator: TabNavigator = koinInject()
    val currentTab by tabNavigator.currentTabFlow.collectAsState()
    val canGoBack by tabNavigator.canGoBack.collectAsState()

    Log.d("BACK_DEBUG", "AppNavigation: canGoBack=$canGoBack, currentTab=$currentTab")

    // ← Теперь BackHandler РЕАГИРУЕТ на изменения canGoBack
    BackHandler(enabled = canGoBack) {
        Log.d("BACK_DEBUG", "BackHandler triggered!")
        tabNavigator.goBack()
    }

    Scaffold(
        modifier = Modifier
            .background(AppTheme.colors.textButton)
            .imePadding(),
        bottomBar = {
            BottomNavigationBar(
                currentTab = currentTab,
                onTabSelected = { tabNavigator.selectTab(it) },
            )
        },
        containerColor = AppTheme.colors.textButton,
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            when (currentTab) {
                BottomTabs.CITIES -> {
                    (citiesApi.citiesTab() as ComposeTabContent).content()
                }
                BottomTabs.MAP -> {
                    (mapApi.mapTab() as ComposeTabContent).content()
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    currentTab: BottomTabs,
    onTabSelected: (BottomTabs) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier.padding(top = 2.dp),
            color = AppTheme.colors.dividerPrimary
        )
        NavigationBar(
            containerColor = AppTheme.colors.textButton
        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.list),
                        contentDescription = "Cities",
                        tint =
                            if (currentTab == BottomTabs.CITIES)
                                AppTheme.colors.accentPrimary
                            else
                                AppTheme.colors.subtitleErrorText
                    )
                },
                selected = currentTab == BottomTabs.CITIES,
                onClick = { onTabSelected(BottomTabs.CITIES) }
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.map),
                        contentDescription = "Map",
                        tint =
                            if (currentTab == BottomTabs.MAP)
                                AppTheme.colors.accentPrimary
                            else
                                AppTheme.colors.subtitleErrorText
                    )
                },
                selected = currentTab == BottomTabs.MAP,
                onClick = { onTabSelected(BottomTabs.MAP) }
            )
        }
    }
}