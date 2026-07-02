package com.example.citiesapplication.core.network.di

import com.example.citiesapplication.core.network.ConnectivityMonitor
import com.example.citiesapplication.core.network.NetworkBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
    single { NetworkBuilder.buildClient() }
    single<ConnectivityMonitor> { ConnectivityMonitor(androidContext()) }
}