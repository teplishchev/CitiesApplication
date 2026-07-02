package com.example.citiesapplication.feature.details.impl.di

import com.example.citiesapplication.feature.details.api.MapFeatureApi
import com.example.citiesapplication.feature.details.impl.navigation.MapFeatureApiImpl
import com.example.citiesapplication.feature.details.impl.data.MapApi
import com.example.citiesapplication.feature.details.impl.data.repository.MapRepositoryImpl
import com.example.citiesapplication.feature.details.impl.domain.MapRepository
import com.example.citiesapplication.feature.details.impl.presentation.MapViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mapModule = module {
    single<MapFeatureApi> {
        MapFeatureApiImpl(tabNavigator = get())
    }
    single<MapApi> { MapApi(client = get()) }
    single<MapRepository> { MapRepositoryImpl(api = get(), connectivityMonitor = get()) }
    viewModelOf(::MapViewModel)
}