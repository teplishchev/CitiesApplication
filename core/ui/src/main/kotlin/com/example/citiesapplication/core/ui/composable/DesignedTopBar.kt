package com.example.citiesapplication.core.ui.composable

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.citiesapplication.core.ui.R
import com.example.citiesapplication.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesignedTopBar(
    title: String,
    hasBackIcon : Boolean = false,
    onBackIconClicked: () -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = AppTheme.colors.textButton,
        ),
        title = {
            Text(
                text = title,
                style = AppTheme.typography.screenTitle,
                color = AppTheme.colors.textPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        navigationIcon = {
            if(hasBackIcon) {
                IconButton(onClick = onBackIconClicked) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_left),
                        contentDescription = "Назад",
                        )
                    }
            }
        },
        windowInsets = WindowInsets(0)
    )
}