package com.example.citiesapplication.core.network.model

sealed class NetworkException(message: String) : Exception(message) {
    data class NoInternet(val errorMessage: String = NO_INTERNET) : NetworkException(errorMessage)
    data class Http(val code: Int, val errorBody: String?) : NetworkException("HTTP Error: $code")
    data class Timeout(val errorMessage: String = TIMEOUT) : NetworkException(errorMessage)
    data class Unknown(override val cause: Throwable?) : NetworkException("Неизвестная ошибка: ${cause?.message}")

    companion object {
        private const val NO_INTERNET = "Нет подключения к интернету"
        private const val TIMEOUT = "Истек лимит ожидания"
    }
}