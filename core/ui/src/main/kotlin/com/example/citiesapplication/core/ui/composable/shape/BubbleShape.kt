package com.example.citiesapplication.core.ui.composable.shape

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

fun createBubblePath(size: Size): Path {
    val w = size.width
    val h = size.height

    val tailHeightRatio = 0.08f
    val tailWidthRatio = 0.04f
    val roundness = 0.90f

    val tailHeight = h * tailHeightRatio
    val bodyHeight = h - tailHeight
    val tailHalfWidth = w * tailWidthRatio
    val invRoundness = 1f - roundness  // 0.1

    return Path().apply {
        // Начинаем сверху по центру
        moveTo(w / 2f, 0f)

        // Правая верхняя дуга
        cubicTo(
            w * roundness, 0f,
            w, bodyHeight * invRoundness,
            w, bodyHeight / 2f
        )

        // Правая нижняя дуга
        cubicTo(
            w, bodyHeight * roundness,
            w * roundness, bodyHeight,
            w / 2f + tailHalfWidth, bodyHeight
        )

        // Хвостик вниз
        lineTo(w / 2f, h)

        // Хвостик вверх
        lineTo(w / 2f - tailHalfWidth, bodyHeight)

        // Левая нижняя дуга
        cubicTo(
            w * invRoundness, bodyHeight,
            0f, bodyHeight * roundness,
            0f, bodyHeight / 2f
        )

        // Левая верхняя дуга
        cubicTo(
            0f, bodyHeight * invRoundness,
            w * invRoundness, 0f,
            w / 2f, 0f
        )

        close()
    }
}

val BubbleShape: Shape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = Outline.Generic(createBubblePath(size))
}