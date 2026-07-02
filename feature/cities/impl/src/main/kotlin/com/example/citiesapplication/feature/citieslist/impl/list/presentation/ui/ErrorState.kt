package com.example.citiesapplication.feature.citieslist.impl.list.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.citiesapplication.core.network.model.NetworkException
import com.example.citiesapplication.core.ui.composable.DesignedButton
import com.example.citiesapplication.core.ui.theme.AppTheme
import com.example.citiesapplication.feature.cities.impl.R

@Composable
internal fun ErrorState(
    error: NetworkException,
    onRetryClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (errorTitle, errorDescription) = when(error) {
        is NetworkException.NoInternet ->
            ErrorInfo(
                title = stringResource(id = R.string.error_no_internet_title),
                description = stringResource(id = R.string.error_no_internet_description)
            )
        else ->
            ErrorInfo(
                title = stringResource(id = R.string.error_unknown_title),
                description = stringResource(id = R.string.error_unknown_description)
            )
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colors.textButton)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_error),
                contentDescription = null,
                modifier = Modifier.size(88.dp),
            )
            Spacer(Modifier.size(24.dp))
            Text(
                text = errorTitle,
                style = AppTheme.typography.errorTitle,
                color = AppTheme.colors.textPrimary,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.size(16.dp))

            Text(
                text = errorDescription,
                style = AppTheme.typography.smallRegularText,
                color = AppTheme.colors.subtitleErrorText,
                textAlign = TextAlign.Center,
            )
        }
        DesignedButton(
            onClick = onRetryClicked,
            text = stringResource(id = R.string.action_retry),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(alignment = Alignment.BottomCenter),
        )
    }
}

data class ErrorInfo(
    val title: String,
    val description: String
)