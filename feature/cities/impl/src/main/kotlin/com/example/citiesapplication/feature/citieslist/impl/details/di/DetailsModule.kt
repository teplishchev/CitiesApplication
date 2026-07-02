package com.example.citiesapplication.feature.citieslist.impl.details.di

import com.example.citiesapplication.feature.citieslist.impl.details.presentation.DetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val DetailsModule = module {
    viewModelOf(::DetailViewModel)
}