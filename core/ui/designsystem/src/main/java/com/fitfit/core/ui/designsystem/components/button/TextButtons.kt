package com.fitfit.core.ui.designsystem.components.button

import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.R

@Composable
fun TryAgainButton(
    onClick: () -> Unit,
    enabled: Boolean
){
    MyTextButton(
        text = stringResource(id = R.string.try_again),
        onClick = onClick,
        enabled = enabled
    )
}

@Composable
fun SettingsButton(
    onClick: () -> Unit,
){
    MyTextButton(
        text = stringResource(id = R.string.settings),
        onClick = onClick,
        modifier = Modifier.widthIn(min = 100.dp)
    )
}