package com.example.citiesapplication.feature.citieslist.impl.list.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.citiesapplication.core.ui.theme.AppTheme
import com.example.citiesapplication.feature.cities.impl.R
import com.example.citiesapplication.feature.citieslist.impl.list.domain.model.City

@Composable
internal fun CityItem(
    city: City,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_geo_pin),
            contentDescription = null,
            tint = AppTheme.colors.subtitleErrorText,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = "${city.name}, ${city.country}",
            style = AppTheme.typography.regularText,
            color = AppTheme.colors.textPrimary,
        )
    }
}