package com.example.citiesapplication.core.network.utils

import com.example.citiesapplication.core.network.ConnectivityMonitor
import com.example.citiesapplication.core.network.model.NetworkException
import com.example.citiesapplication.core.network.model.NetworkResult
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.UnknownHostException
import java.net.SocketTimeoutException

fun <T> safeApiCall(
    networkMonitor: ConnectivityMonitor,
    apiCall: suspend () -> T
): Flow<NetworkResult<T>> = flow {

    if (!networkMonitor.isOnline.value) {
        emit(NetworkResult.Error(NetworkException.NoInternet()))
        return@flow
    }

    emit(NetworkResult.Loading)

    try {
        val result = apiCall()
        emit(NetworkResult.Success(result))
    } catch (e: ResponseException) {
        val status = e.response.status.value
        val errorBody = try { e.response.bodyAsText() } catch (ex: Exception) { null }
        emit(NetworkResult.Error(NetworkException.Http(status, errorBody)))
    } catch (e: SocketTimeoutException) {
        emit(NetworkResult.Error(NetworkException.Timeout()))
    } catch (e: UnknownHostException) {
        emit(NetworkResult.Error(NetworkException.NoInternet()))
    } catch (e: IOException) {
        emit(NetworkResult.Error(NetworkException.NoInternet()))
    } catch (e: Exception) {
        emit(NetworkResult.Error(NetworkException.Unknown(e)))
    }
}