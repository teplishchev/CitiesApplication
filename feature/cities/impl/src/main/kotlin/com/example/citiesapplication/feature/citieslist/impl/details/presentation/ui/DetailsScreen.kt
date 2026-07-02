package com.example.citiesapplication.feature.citieslist.impl.details.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.citiesapplication.core.ui.composable.DesignedButton
import com.example.citiesapplication.core.ui.composable.DesignedTopBar
import com.example.citiesapplication.core.ui.composable.city.CityDetailsColumn
import com.example.citiesapplication.feature.cities.impl.R
import com.example.citiesapplication.feature.citieslist.impl.details.presentation.DetailViewModel
import com.example.citiesapplication.feature.citieslist.impl.details.presentation.model.DetailSideEffect
import com.example.citiesapplication.feature.citieslist.impl.details.presentation.model.DetailState
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun DetailsScreen(
    onBack: () -> Unit,
    viewModel: DetailViewModel = koinViewModel(),
) {
    val state by viewModel.collectAsState()
    val uriHandler = LocalUriHandler.current

    viewModel.collectSideEffect { effect ->
        when (effect) {
            DetailSideEffect.Back -> onBack()
            is DetailSideEffect.OpenWebSearch -> uriHandler.openUri(effect.url)
        }
    }

    DetailsContent(
        state = state,
        onBack = viewModel::onBackClicked,
        onSearchWeb = viewModel::onLoadWebInfoClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsContent(
    state: DetailState,
    onBack: () -> Unit,
    onSearchWeb: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DesignedTopBar(
            title = stringResource(R.string.detail_title),
            hasBackIcon = true,
            onBackIconClicked = onBack
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            CityDetailsColumn(
                name = state.name,
                country = state.country,
                population = state.population
            )

            DesignedButton(
                onClick = onSearchWeb,
                text = stringResource(id = R.string.detail_search_web),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}