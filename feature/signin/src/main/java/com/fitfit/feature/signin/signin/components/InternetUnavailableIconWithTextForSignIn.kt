package com.fitfit.feature.signin.signin.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import com.fitfit.core.ui.ui.InternetUnavailableIconWithText

@Composable
internal fun InternetUnavailableIconWithTextForSignIn(
    visible: Boolean
){
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(400)),
        exit = fadeOut(tween(400))
    ) {
        InternetUnavailableIconWithText()
    }
}