package com.example.citiesapplication.feature.citieslist.impl.list.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.citiesapplication.core.ui.composable.DesignedTopBar
import com.example.citiesapplication.core.ui.composable.SearchField
import com.example.citiesapplication.core.ui.theme.AppTheme
import com.example.citiesapplication.feature.cities.impl.R
import com.example.citiesapplication.feature.citieslist.impl.list.domain.model.City
import com.example.citiesapplication.feature.citieslist.impl.list.presentation.CitiesListViewModel
import com.example.citiesapplication.feature.citieslist.impl.list.presentation.model.CitiesListAction
import com.example.citiesapplication.feature.citieslist.impl.list.presentation.model.CitiesListSideEffect
import com.example.citiesapplication.feature.citieslist.impl.list.presentation.model.CitiesListState
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun CitiesListScreen(
    onNavigateToDetails: (City) -> Unit,
    viewModel: CitiesListViewModel = koinViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val loadMoreFailedMessage = stringResource(R.string.error_loading)

    val state by viewModel.collectAsState()
    viewModel.collectSideEffect { effect ->
        when (effect) {
            is CitiesListSideEffect.ShowErrorSnackbar ->
                snackbarHostState.showSnackbar(loadMoreFailedMessage)

            is CitiesListSideEffect.NavigateToDetailsScreen ->
                onNavigateToDetails(effect.city)
        }
    }

    CitiesListContent(
        state = state,
        onQueryChange = { query ->
            viewModel.dispatch(CitiesListAction.OnSearchQueryChanged(query))
        },
        onLoadMore = { viewModel.dispatch(CitiesListAction.LoadNextPage) },
        onCityClick = viewModel::onCityClicked,
        onRetry = viewModel::onRetry
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CitiesListContent(
    state: CitiesListState,
    onQueryChange: (String) -> Unit,
    onLoadMore: () -> Unit,
    onCityClick: (City) -> Unit,
    onRetry: () -> Unit
) {
    if (state.error != null) {
        ErrorState(error = state.error, onRetryClicked = onRetry)
        return
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        DesignedTopBar(
            title = stringResource(R.string.cities_list_title)
        )

        SearchField(
            query = state.query,
            onQueryChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.size(8.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> CitiesListShimmer()
                state.isEmpty -> EmptyListState()
                else -> CitiesList(
                    state = state,
                    onLoadMore = onLoadMore,
                    onCityClick = onCityClick,
                )
            }
        }
    }
}

@Composable
private fun CitiesList(
    state: CitiesListState,
    onLoadMore: () -> Unit,
    onCityClick: (City) -> Unit,
) {
    val listState = rememberLazyListState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = listState.layoutInfo.totalItemsCount
            state.canLoadMore && !state.isLoadingMore && total > 0 &&
                    lastVisible >= total - 5
        }
    }
    if (shouldLoadMore) {
        LaunchedEffect(state.cities.size, state.query) { onLoadMore() }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(items = state.cities, key = { it.id }) { city ->
            CityItem(
                city = city,
                onClick = { onCityClick(city) })
            if (city != state.cities.last()) {
                HorizontalDivider(color = AppTheme.colors.dividerPrimary)
            }
        }
        if (state.isLoadingMore) {
            item {
                Spacer(Modifier.size(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}