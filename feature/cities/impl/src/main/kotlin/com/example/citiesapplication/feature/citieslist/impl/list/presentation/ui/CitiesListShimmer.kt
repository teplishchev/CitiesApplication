package com.example.citiesapplication.feature.citieslist.impl.list.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.citiesapplication.core.ui.composable.shimmer.ShimmerRectangle
import com.example.citiesapplication.core.ui.theme.AppTheme
import com.example.citiesapplication.feature.cities.impl.R

private val cornerRadius = 12.dp
private const val maxShimmerRows = 5
private const val smallRowSize = 0.5f
private const val mediumRowSize = 0.6f

@Composable
internal fun CitiesListShimmer(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        (0 until maxShimmerRows).forEach { index ->
            CityItemShimmer(widthFraction = if (index % 2 == 0) smallRowSize else mediumRowSize)
            if (index < maxShimmerRows) {
                HorizontalDivider(color = AppTheme.colors.dividerPrimary)
            }
        }
    }
}

@Composable
private fun CityItemShimmer(
    widthFraction: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 16.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_geo_pin),
            contentDescription = null,
            tint = AppTheme.colors.subtitleErrorText,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.size(12.dp))
        ShimmerRectangle(
            modifier = Modifier
                .fillMaxWidth(fraction = widthFraction)
                .height(22.dp)
                .clip(RoundedCornerShape(cornerRadius)),
            cornerRadius = cornerRadius
        )
    }
}