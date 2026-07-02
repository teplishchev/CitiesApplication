package com.example.citiesapplication.core.ui.composable.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.citiesapplication.core.ui.theme.AppTheme

@Composable
fun ShimmerRectangle(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp
) {
    Box(
        modifier = modifier
            .background(
                color = AppTheme.colors.shimmerBackground.copy(alpha = 0.3f),
                shape = RoundedCornerShape(cornerRadius)
            )
            .shimmer()
    )
}