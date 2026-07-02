package com.example.citiesapplication.feature.citieslist.impl.list.domain.mapper

import com.example.citiesapplication.feature.citieslist.api.CityInfo
import com.example.citiesapplication.feature.citieslist.impl.list.domain.model.City

internal fun City.toDetailCity() = CityInfo(
    id = id,
    name = name,
    country = country,
    pop = pop
)