package com.fitfit.core.ui.designsystem.components.button

import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.icon.IconButtonIcon

@Composable
fun MinusButton(
    onClick: () -> Unit
){
    MyIconButton(
        icon = IconButtonIcon.minus,
        onClick = onClick,
        modifier = Modifier.size(50.dp),
        containerColor = MaterialTheme.colorScheme.surfaceDim
    )
}

@Composable
fun PlusButton(
    onClick: () -> Unit
){
    MyIconButton(
        icon = IconButtonIcon.plus,
        onClick = onClick,
        modifier = Modifier.size(50.dp),
        containerColor = MaterialTheme.colorScheme.surfaceDim
    )
}