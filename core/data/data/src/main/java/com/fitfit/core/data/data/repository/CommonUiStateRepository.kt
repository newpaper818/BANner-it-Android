package com.fitfit.core.data.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

data class CommonUiState(
    val example: Boolean = false
)

@Singleton
class CommonUiStateRepository @Inject constructor() {
    val _commonUiState: MutableStateFlow<CommonUiState> =
        MutableStateFlow(
            CommonUiState()
        )

    val commonUiState = _commonUiState.asStateFlow()
}