package com.fitfit.bannerit.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.fitfit.bannerit.utils.WindowSizeClass

@Composable
fun rememberExternalState(
    windowSizeClass: WindowSizeClass,
    internetEnabled: Boolean,
): ExternalState {
    return remember(
        windowSizeClass,
        internetEnabled
    ){
        ExternalState(
            windowSizeClass = windowSizeClass,
            internetEnabled = internetEnabled
        )
    }
}

@Stable
class ExternalState(
    val windowSizeClass: WindowSizeClass,
    val internetEnabled: Boolean
)