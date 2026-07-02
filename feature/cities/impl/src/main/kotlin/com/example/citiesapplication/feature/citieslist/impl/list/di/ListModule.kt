package com.example.citiesapplication.feature.citieslist.impl.list.di

import com.example.citiesapplication.feature.citieslist.api.CitiesFeatureApi
import com.example.citiesapplication.feature.citieslist.impl.CitiesFeatureApiImpl
import com.example.citiesapplication.feature.citieslist.impl.list.data.CitiesListApi
import com.example.citiesapplication.feature.citieslist.impl.list.data.repository.CitiesListRepositoryImpl
import com.example.citiesapplication.feature.citieslist.impl.list.domain.repository.CitiesListRepository
import com.example.citiesapplication.feature.citieslist.impl.list.presentation.CitiesListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.binds
import org.koin.dsl.module

val ListModule = module {
    single<CitiesListApi> { CitiesListApi(client = get()) }
    single<CitiesListRepository> { CitiesListRepositoryImpl(api = get(), connectivityMonitor = get()) }
    viewModelOf(::CitiesListViewModel)
//    singleOf(::CitiesFeatureApiImpl) binds arrayOf(CitiesFeatureApi::class, FeatureGraph::class)
}