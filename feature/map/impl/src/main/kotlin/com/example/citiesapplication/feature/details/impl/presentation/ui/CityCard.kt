package com.example.citiesapplication.feature.details.impl.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.citiesapplication.core.ui.composable.shape.BubbleShape
import com.example.citiesapplication.core.ui.composable.shape.createBubblePath
import com.example.citiesapplication.core.ui.theme.AppTheme
import com.example.citiesapplication.feature.details.impl.domain.model.City

@Composable
internal fun CityCard(
    city: City,
    isChecked: Boolean,
    onNavigateToDetails: (City) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isChecked) {
        AppTheme.colors.accentPrimary
    } else {
        AppTheme.colors.pinBackground
    }
    val borderColor = AppTheme.colors.textButton
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Bubble (овал + выступ)
        Box(
            modifier = Modifier
                .padding(2.dp)
                .drawWithCache {
                    val path = createBubblePath(size)
                    onDrawBehind {
                        drawPath(path, color = backgroundColor)
                        drawPath(
                            path,
                            color = borderColor,
                            style = Stroke(width = 1.dp.toPx())
                        )
                    }
                }
                .clip(BubbleShape)
                .clickable {
                    onNavigateToDetails(city)
                }
        ) {
            // Контент внутри bubble
            Box(
                modifier = Modifier
                    .padding(
                        top = 6.dp,
                        bottom = 8.dp, // Отступ снизу, чтобы текст не залезал на выступ
                        start = 6.dp,
                        end = 6.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = city.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = AppTheme.colors.textButton,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Точка на карте (отдельный элемент)
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                )
                .shadow(4.dp, CircleShape)
        )
    }
}