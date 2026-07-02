package com.example.citiesapplication.feature.citieslist.impl.di

import com.example.citiesapplication.feature.citieslist.api.CitiesFeatureApi
import com.example.citiesapplication.feature.citieslist.impl.CitiesFeatureApiImpl
import com.example.citiesapplication.feature.citieslist.impl.details.di.DetailsModule
import com.example.citiesapplication.feature.citieslist.impl.list.di.ListModule
import org.koin.dsl.module

val citiesModule = module {
    includes(
        ListModule,
        DetailsModule,
    )

    single<CitiesFeatureApi> {
        CitiesFeatureApiImpl(tabNavigator = get())
    }
}