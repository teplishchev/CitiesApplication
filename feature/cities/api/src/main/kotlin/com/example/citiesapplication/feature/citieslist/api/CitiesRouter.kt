package com.example.citiesapplication.feature.citieslist.api

interface CitiesRouter {
    fun navigateToCityDetails(cityInfo: CityInfo)
    fun goBack()
}