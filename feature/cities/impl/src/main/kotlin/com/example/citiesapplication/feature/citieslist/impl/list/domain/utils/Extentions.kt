package com.example.citiesapplication.feature.citieslist.impl.list.domain.utils

import java.util.Locale

fun String.toCountryName(): String {
    return Locale("", this).getDisplayCountry(Locale("ru"))
}