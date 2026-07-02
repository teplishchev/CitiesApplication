package com.example.citiesapplication.core.ui.composable.city

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.citiesapplication.core.ui.R
import com.example.citiesapplication.core.ui.theme.AppTheme

@Composable
fun CityDetailsColumn(
    name: String,
    country: String,
    population: Long
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        DetailItem(
            title = stringResource(R.string.detail_name),
            value = name
        )
        DetailItem(
            title = stringResource(R.string.detail_country),
            value = country
        )
        DetailItem(
            title = stringResource(R.string.detail_population),
            value = "${population.toPopulationFormat()} ${stringResource(R.string.detail_population_unit)}",
        )
    }
}

@Composable
private fun DetailItem(title: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = title,
            style = AppTheme.typography.regularText,
            color = AppTheme.colors.textPrimary,
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = value,
            style = AppTheme.typography.smallRegularText,
            color = AppTheme.colors.textPrimary,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}

