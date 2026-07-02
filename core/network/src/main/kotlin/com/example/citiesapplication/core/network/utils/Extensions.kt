package com.example.citiesapplication.core.network.utils

import android.net.Uri

fun String.toSearchUrl() =
    "https://www.google.com/search?q=${Uri.encode(this)}"