package com.fitfit.core.ui.designsystem.components.button

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.utils.ClickableBox
import com.fitfit.core.ui.designsystem.icon.IconButtonIcon

@Composable
fun CloseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    MyIconButton(
        modifier = modifier.size(50.dp),
        icon = IconButtonIcon.closeCamera,
        onClick = onClick,
        containerColor = Color.DarkGray
    )
}

@Composable
fun ShutterButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    ClickableBox(
        modifier = modifier.size(70.dp),
        onClick = onClick,
        containerColor = Color.White,
        shape = CircleShape,
    ) {

    }
}