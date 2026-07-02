package com.example.citiesapplication.feature.details.impl.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.citiesapplication.core.ui.composable.DesignedButton
import com.example.citiesapplication.core.ui.composable.city.CityDetailsColumn
import com.example.citiesapplication.core.ui.theme.AppTheme
import com.example.citiesapplication.feature.details.impl.domain.model.City
import com.example.citiesapplication.feature.map.impl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CityBottomSheet(
    city: City,
    onDismiss: () -> Unit,
    onOpenInBrowser: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true // Полностью раскрытый сразу
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppTheme.colors.textButton,
        shape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp
        ),
        dragHandle = {
            // Кастомный drag handle
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }
    ) {
        CityBottomSheetContent(
            city = city,
            onOpenInBrowser = onOpenInBrowser,
        )
    }
}

@Composable
private fun CityBottomSheetContent(
    city: City,
    onOpenInBrowser: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
    ) {
        CityDetailsColumn(
            name = city.name,
            country = city.country,
            population = city.pop
        )

        Spacer(modifier = Modifier.height(8.dp))

        DesignedButton(
            onClick = onOpenInBrowser,
            text = stringResource(id = R.string.detail_search_web),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
    }
}