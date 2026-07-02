package com.example.citiesapplication.core.ui.composable

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.citiesapplication.core.ui.theme.AppTheme

@Composable
fun DesignedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor: Color =
        if (isPressed) AppTheme.colors.buttonPressed else AppTheme.colors.accentPrimary

    Button(
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            disabledContainerColor = AppTheme.colors.backgroundTertiary,
            contentColor = AppTheme.colors.textButton,
            disabledContentColor = AppTheme.colors.textTertiary,
        ),
        contentPadding = PaddingValues(horizontal = 24.dp),
    ) {
        Text(
            text = text,
            style = AppTheme.typography.button,
        )
    }
}