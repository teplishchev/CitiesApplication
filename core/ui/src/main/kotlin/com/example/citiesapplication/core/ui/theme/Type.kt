package com.example.citiesapplication.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class AppTypography(

    val errorTitle: TextStyle,
    val screenTitle: TextStyle,

    val regularText: TextStyle,
    val smallRegularText: TextStyle,

    val button: TextStyle,
)

fun getAppTypography() = AppTypography(
    errorTitle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 23.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
    ),

    screenTitle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 19.sp,
        lineHeight = 26.sp,
    ),

    regularText = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp,
    ),

    button = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 26.sp,
    ),

    smallRegularText = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp,
    ),
)