package com.fitfit.bannerit.utils.internetConnectivityObserver

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    val isConnected: Flow<Boolean>
}