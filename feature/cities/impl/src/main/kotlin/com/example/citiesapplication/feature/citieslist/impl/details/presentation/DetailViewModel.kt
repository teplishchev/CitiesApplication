package com.example.citiesapplication.feature.citieslist.impl.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.citiesapplication.core.network.utils.toSearchUrl
import com.example.citiesapplication.feature.citieslist.impl.details.presentation.model.DetailSideEffect
import com.example.citiesapplication.feature.citieslist.impl.details.presentation.model.DetailState
import com.example.citiesapplication.feature.citieslist.impl.navigation.CityDetailRoute
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container

internal class DetailViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<DetailState, DetailSideEffect> {

    private val route = savedStateHandle.toRoute<CityDetailRoute>()

    override val container = container<DetailState, DetailSideEffect>(
        DetailState(
            name = route.name,
            country = route.country,
            population = route.population,
        ),
    )

    fun onLoadWebInfoClick() = intent {
        val url = state.name.toSearchUrl()
        postSideEffect(DetailSideEffect.OpenWebSearch(url))
    }

    fun onBackClicked() = intent {
        postSideEffect(DetailSideEffect.Back)
    }
}