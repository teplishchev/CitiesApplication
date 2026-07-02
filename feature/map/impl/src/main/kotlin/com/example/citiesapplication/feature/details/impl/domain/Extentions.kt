package com.example.citiesapplication.feature.details.impl.domain

import java.util.Locale

fun String.toCountryName(): String {
    return Locale("", this).getDisplayCountry(Locale("ru"))
}