package com.example.citiesapplication.feature.citieslist.impl.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citiesapplication.core.network.model.NetworkResult
import com.example.citiesapplication.feature.citieslist.impl.list.domain.model.City
import com.example.citiesapplication.feature.citieslist.impl.list.domain.repository.CitiesListRepository
import com.example.citiesapplication.feature.citieslist.impl.list.presentation.model.CitiesListAction
import com.example.citiesapplication.feature.citieslist.impl.list.presentation.model.CitiesListSideEffect
import com.example.citiesapplication.feature.citieslist.impl.list.presentation.model.CitiesListState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@OptIn(FlowPreview::class)
internal class CitiesListViewModel(
    private val citiesListRepository: CitiesListRepository,
) : ContainerHost<CitiesListState, CitiesListSideEffect>, ViewModel() {

    override val container = container<CitiesListState, CitiesListSideEffect>(CitiesListState()) {
        observeSearchQuery()
    }

    private val searchQueryFlow = MutableSharedFlow<String>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        dispatch(CitiesListAction.LoadCitiesList)
    }

    fun dispatch(action: CitiesListAction) {
        viewModelScope.launch {
            when (action) {
                CitiesListAction.LoadCitiesList -> loadPage()
                CitiesListAction.LoadNextPage -> loadPage(initial = false, loadNextPage = true)
                is CitiesListAction.OnSearchQueryChanged -> onSearchQueryChange(action.query)
            }
        }
    }

    fun onCityClicked(city: City) = intent {
        postSideEffect(CitiesListSideEffect.NavigateToDetailsScreen(city))
    }

    fun onRetry() = intent {
        reduce { state.copy(error = null) }
        dispatch(CitiesListAction.LoadCitiesList)
    }

    private fun onSearchQueryChange(query: String) = intent {
        reduce { state.copy(query = query) }
        searchQueryFlow.emit(query)
    }

    private fun observeSearchQuery() = intent {
        searchQueryFlow
            .debounce(SEARCH_DEBOUNCE_MILLIS)
            .distinctUntilChanged()
            .collectLatest { query ->
                loadPage(query = query)
            }
    }

    private fun loadPage(
        query: String? = null,
        initial: Boolean = true,
        loadNextPage: Boolean = false
    )  = intent {
        val nextLoadingPage = if (loadNextPage) state.currentPage + 1 else START_PAGE
        val newQuery = query ?: state.query

        if (initial) {
            reduce {
                state.copy(
                    query = newQuery,
                    currentPage = nextLoadingPage,
                    isLoadingMore = false,
                    isLoading = true,
                    error = null
                )
            }
        } else {
            reduce {
                state.copy(
                    query = newQuery,
                    currentPage = nextLoadingPage,
                    isLoadingMore = true,
                    isLoading = false,
                    error = null
                )
            }
        }

        citiesListRepository.getCitiesList(
            query = newQuery,
            page = nextLoadingPage,
            limit = PAGE_SIZE
        ).collect { result ->
            when (result) {
                is NetworkResult.Loading -> Unit

                is NetworkResult.Error -> {
                    reduce {
                        state.copy(
                            isLoading = false,
                            isLoadingMore = false,
                            error = result.exception
                        )
                    }
                    postSideEffect(
                        CitiesListSideEffect.ShowErrorSnackbar(
                            result.exception.message ?: "Неизвестная ошибка"
                        )
                    )
                }

                is NetworkResult.Success -> {
                    val newCities = result.data.cities
                    reduce {
                        state.copy(
                            cities = if (!initial) state.cities + newCities else newCities,
                            query = newQuery,
                            currentPage = nextLoadingPage,
                            isLoading = false,
                            isLoadingMore = false,
                            canLoadMore = (nextLoadingPage + 1) * PAGE_SIZE < result.data.total
                        )
                    }
                }
            }
        }
    }

    private companion object {
        const val START_PAGE = 1
        const val PAGE_SIZE = 20
        const val SEARCH_DEBOUNCE_MILLIS = 500L
    }
}