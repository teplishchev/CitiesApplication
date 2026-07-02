package com.example.citiesapplication.core.ui.composable

import androidx.compose.runtime.Composable
import com.example.citiesapplication.core.navigation.bottomnavigation.TabContent

class ComposeTabContent(
    val content: @Composable () -> Unit
) : TabContent