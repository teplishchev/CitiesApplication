package com.example.citiesapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.citiesapplication.core.ui.theme.CitiesApplicationTheme
import com.example.citiesapplication.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CitiesApplicationTheme {
                AppNavigation()
            }
        }
    }
}