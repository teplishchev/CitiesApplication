package com.example.citiesapplication.core.navigation.di

import com.example.citiesapplication.core.navigation.bottomnavigation.TabNavigator
import com.example.citiesapplication.core.navigation.bottomnavigation.TabNavigatorImpl
import org.koin.dsl.module

val navigationModule = module {
    single<TabNavigator> { TabNavigatorImpl() }
}