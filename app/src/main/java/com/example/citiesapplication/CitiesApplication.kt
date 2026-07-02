package com.example.citiesapplication

import android.app.Application
import com.example.citiesapplication.core.navigation.di.navigationModule
import com.example.citiesapplication.core.network.di.networkModule
import com.example.citiesapplication.feature.citieslist.impl.di.citiesModule
import com.example.citiesapplication.feature.details.impl.di.mapModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.osmdroid.config.Configuration
import java.io.File

class CitiesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CitiesApplication)
            modules(navigationModule, networkModule, citiesModule, mapModule)
        }

        Configuration.getInstance().apply {
            userAgentValue = "CitiesApplication/1.0"
            osmdroidBasePath = File(filesDir, "osmdroid")
            osmdroidTileCache = File(cacheDir, "osmdroid_tiles")
        }
    }
}