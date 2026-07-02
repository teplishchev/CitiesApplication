package com.example.citiesapplication.feature.details.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citiesapplication.core.network.model.NetworkResult
import com.example.citiesapplication.core.network.utils.toSearchUrl
import com.example.citiesapplication.feature.details.impl.domain.MapRepository
import com.example.citiesapplication.feature.details.impl.domain.toCountryName
import com.example.citiesapplication.feature.details.impl.presentation.data.MapAction
import com.example.citiesapplication.feature.details.impl.presentation.data.MapSideEffect
import com.example.citiesapplication.feature.details.impl.presentation.data.MapSideEffect.*
import com.example.citiesapplication.feature.details.impl.presentation.data.MapState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitInternal
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@OptIn(FlowPreview::class)
internal class MapViewModel(
    private val repository: MapRepository
) : ViewModel(), ContainerHost<MapState, MapSideEffect> {

    override val container = container<MapState, MapSideEffect>(MapState())

    private val cameraEventFlow = MutableSharedFlow<CameraEvent>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            cameraEventFlow
                .debounce(500L)
                .distinctUntilChanged()
                .collect { event ->
                    intent {
                        loadCities(event)
                    }
                }
        }
    }

    fun dispatch(action: MapAction) {
        viewModelScope.launch(Dispatchers.Main) {
            when (action) {
                MapAction.LoadInitialData -> {
                    // Начальные координаты (Москва)
                    val initialRadius = 90000 // 90 км
                    loadCities(
                        CameraEvent(
                        MAX_ZOOM_LEVEL,
                            55.7558,
                            37.6173,
                            initialRadius
                        )
                    )
                }

                is MapAction.OnCameraChanged -> {
                    cameraEventFlow.emit(
                        CameraEvent(
                            action.zoomLevel,
                            action.centerLat,
                            action.centerLng,
                            action.radius
                        )
                    )
                }

                is MapAction.OnCityClick -> {
                    intent {
                        reduce {
                            state.copy(
                                checkedCity = action.city.copy(
                                    country = action.city.country.toCountryName()
                                )
                            )
                        }
                    }
                }

                MapAction.OnBottomSheetDismiss -> {
                    intent {
                        reduce { state.copy(checkedCity = null) }
                    }
                }

                MapAction.OnOpenInBrowser -> {
                    intent {
                        val city = state.checkedCity ?: return@intent
                        postSideEffect(OpenInBrowser(city.name.toSearchUrl()))
                    }
                }
            }
        }
    }

    @OptIn(OrbitInternal::class)
    private suspend fun loadCities(
        event: CameraEvent
    ) = intent {

        reduce { state.copy(isLoading = true) }

        if (event.zoomLevel <= MAX_ZOOM_LEVEL) {
            reduce { state.copy(cities = emptyList()) }
        } else {
            repository.getCitiesForMap(
                centerLat = event.centerLat,
                centerLng = event.centerLng,
                radius = event.radius
            ).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        reduce {
                            state.copy(
                                cities = result.data.sortedByDescending { it.pop }.take(10),
                                isLoading = false
                            )
                        }
                    }

                    is NetworkResult.Error -> {
                        reduce {
                            state.copy(
                                isLoading = false,
                                error = result.exception.message
                            )
                        }
                    }

                    is NetworkResult.Loading -> Unit
                }
            }
        }
    }

    companion object {
        const val MAX_ZOOM_LEVEL = 10.0
    }
}

private data class CameraEvent(
    val zoomLevel: Double,
    val centerLat: Double,
    val centerLng: Double,
    val radius: Int
)