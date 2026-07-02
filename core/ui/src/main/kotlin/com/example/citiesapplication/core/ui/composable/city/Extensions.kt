package com.example.citiesapplication.core.ui.composable.city

import java.util.Locale

internal fun Long.toPopulationFormat(): String =
    String.format(Locale.getDefault(), "%,d", this)
        .replace(',', ' ')