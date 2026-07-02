package com.example.citiesapplication.feature.citieslist.impl.list.data.mapper

import com.example.citiesapplication.feature.citieslist.impl.list.data.dto.CitiesListResponseDto
import com.example.citiesapplication.feature.citieslist.impl.list.data.dto.CityDto
import com.example.citiesapplication.feature.citieslist.impl.list.domain.model.CitiesListPage
import com.example.citiesapplication.feature.citieslist.impl.list.domain.model.City
import com.example.citiesapplication.feature.citieslist.impl.list.domain.utils.toCountryName

internal fun CityDto.toDomain(): City = City(
    id = id,
    name = name,
    country = country.toCountryName(),
    pop = pop,
)

internal fun CitiesListResponseDto.toDomain(): CitiesListPage = CitiesListPage(
    cities = items.map { it.toDomain() },
    page = page,
    limit = limit,
    total = total,
)