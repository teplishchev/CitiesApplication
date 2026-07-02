package com.example.citiesapplication.feature.details.impl.data.mapper

import com.example.citiesapplication.feature.details.impl.data.dto.CityDto
import com.example.citiesapplication.feature.details.impl.domain.model.City

internal fun CityDto.toDomain() = City(
    id = id,
    name = name,
    country = country,
    pop = pop,
    lat = lat,
    lng = lon
)