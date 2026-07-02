package com.example.citiesapplication.feature.details.impl.presentation.ui

import android.content.Context
import android.graphics.Point
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.viewinterop.AndroidView
import com.example.citiesapplication.feature.details.impl.domain.model.City
import com.example.citiesapplication.feature.details.impl.presentation.MapViewModel
import com.example.citiesapplication.feature.details.impl.presentation.MapViewModel.Companion.MAX_ZOOM_LEVEL
import com.example.citiesapplication.feature.details.impl.presentation.data.MapAction
import com.example.citiesapplication.feature.details.impl.presentation.data.MapSideEffect
import com.example.citiesapplication.feature.details.impl.presentation.data.ScreenPosition
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectSideEffect
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

@Composable
internal fun MapScreen(
    viewModel: MapViewModel = koinViewModel()
) {
    val state by viewModel.container.stateFlow.collectAsState()
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    val mapViewState = remember { mutableStateOf<MapView?>(null) }
    val screenPositions = remember { mutableStateListOf<ScreenPosition>() }
    var isInitialized by remember { mutableStateOf(false) }
    var onCameraChanged by remember { mutableStateOf<(MapView) -> Unit>({ }) }

    viewModel.collectSideEffect { effect ->
        when (effect) {
            is MapSideEffect.OpenInBrowser -> uriHandler.openUri(effect.url)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.dispatch(MapAction.LoadInitialData)
    }

    // Обновляем callback при изменении state.cities
    LaunchedEffect(state.cities) {
        onCameraChanged = { mapView ->
            sendCameraUpdate(mapView, viewModel)
            updateScreenPositions(mapView, state.cities, screenPositions)
        }
        // Пересчитываем позиции при изменении списка
        mapViewState.value?.let { mapView ->
            updateScreenPositions(mapView, state.cities, screenPositions)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                MapView(ctx).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    controller.setZoom(MAX_ZOOM_LEVEL)
                    controller.setCenter(GeoPoint(55.7558, 37.6173))

                    setMapListener(object : MapListener {
                        override fun onScroll(event: ScrollEvent?): Boolean {
                            if (isInitialized) {
                                onCameraChanged(this@apply)
                            }
                            return true
                        }

                        override fun onZoom(event: ZoomEvent?): Boolean {
                            if (isInitialized) {
                                onCameraChanged(this@apply)
                            }
                            return true
                        }
                    })

                    mapViewState.value = this

                    postDelayed({
                        isInitialized = true
                        onCameraChanged(this)
                    }, 500)
                }
            }
        )

        state.cities.forEach { city ->
            val position = screenPositions.find { it.city.id == city.id }
            val cardSize = remember { mutableStateOf(IntSize.Zero) }

            if (position != null && isPositionVisible(position, context)) {
                CityCard(
                    city = city,
                    isChecked = city == state.checkedCity,
                    onNavigateToDetails = { city -> viewModel.dispatch(MapAction.OnCityClick(city)) },
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            cardSize.value = coordinates.size
                        }
                        .offset {
                            IntOffset(
                                // Центрируем по X: половина ширины карточки
                                x = (position.x - cardSize.value.width / 2f).roundToInt(),
                                // Пин внизу карточки — поднимаем на всю высоту
                                y = (position.y - cardSize.value.height).roundToInt()
                            )
                        }
                )
            }
        }

        state.checkedCity?.let { city ->
            CityBottomSheet(
                city = city,
                onDismiss = { viewModel.dispatch(MapAction.OnBottomSheetDismiss) },
                onOpenInBrowser = { viewModel.dispatch(MapAction.OnOpenInBrowser) },
            )
        }
    }
}

private fun sendCameraUpdate(
    mapView: MapView,
    viewModel: MapViewModel
) {
    val center = mapView.mapCenter
    val centerLat = center.latitude
    val centerLng = center.longitude
    val zoom = mapView.zoomLevelDouble
    val radius = calculateRadiusInMeters(mapView, centerLat)

    viewModel.dispatch(
        MapAction.OnCameraChanged(
            zoomLevel = zoom,
            centerLat = centerLat,
            centerLng = centerLng,
            radius = radius
        )
    )
}

private fun calculateRadiusInMeters(
    mapView: MapView,
    centerLat: Double
): Int {
    val screenWidthPixels = mapView.width.toDouble()
    val zoomLevel = mapView.zoomLevelDouble

    val metersPerPixel = 156543.03392 *
            cos(Math.toRadians(centerLat)) /
            2.0.pow(zoomLevel)

    val widthMeters = screenWidthPixels * metersPerPixel
    val heightMeters = mapView.height * metersPerPixel
    val radiusMeters = sqrt(
        (widthMeters / 2).pow(2.0) + (heightMeters / 2).pow(2.0)
    )

    return radiusMeters.toInt()
}

private fun updateScreenPositions(
    mapView: MapView,
    cities: List<City>,
    screenPositions: MutableList<ScreenPosition>
) {
    screenPositions.clear()
    val projection = mapView.projection

    cities.forEach { city ->
        val geoPoint = GeoPoint(city.lat, city.lng)
        val point = Point()
        projection.toPixels(geoPoint, point)

        screenPositions.add(
            ScreenPosition(
                city = city,
                x = point.x.toFloat(),
                y = point.y.toFloat()
            )
        )
    }
}

private fun isPositionVisible(
    position: ScreenPosition,
    context: Context
): Boolean {
    val displayMetrics = context.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels.toFloat()
    val screenHeight = displayMetrics.heightPixels.toFloat()

    // Добавляем отступы, чтобы карточки не обрезались
    val padding = 150f
    return position.x in -padding..(screenWidth + padding) &&
            position.y in -padding..(screenHeight + padding)
}
