package com.fitfit.bannerit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fitfit.bannerit.ui.AppViewModel
import com.fitfit.bannerit.ui.BannerItApp
import com.fitfit.bannerit.ui.rememberExternalState
import com.fitfit.bannerit.utils.calculateWindowSizeClass
import com.fitfit.bannerit.utils.internetConnectivityObserver.AndroidConnectivityObserver
import com.fitfit.bannerit.utils.internetConnectivityObserver.ConnectivityViewModel
import com.fitfit.core.model.enums.AppTheme
import com.fitfit.core.ui.designsystem.theme.BannerItTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val MAIN_ACTIVITY_TAG = "MainActivity1"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        //splash screen ----------------------------------------------------------------------------------------
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            appViewModel.appUiState.value.screenDestination.startScreenDestination == null
        }

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        val internetEnabled = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        //get signed user and update start destination ---------------------------------------------------------
        appViewModel.viewModelScope.launch {

            //get signed user and update start destination
            Log.d(MAIN_ACTIVITY_TAG, "- init user and update start destination start")

            //this function will get user and set {appViewModel.appUiState.value.screenDestination.startScreenDestination}
            appViewModel.intiUserAndUpdateStartDestination(
                internetEnabled = internetEnabled
            )
        }

        enableEdgeToEdge()

        // ----------------------------------------------------------------------------------------
        setContent {
            val connectivityViewModel = viewModel<ConnectivityViewModel> {
                ConnectivityViewModel(
                    connectivityObserver = AndroidConnectivityObserver(
                        context = applicationContext
                    )
                )
            }

            val internetEnabled by connectivityViewModel.isConnected.collectAsState()
            val appUiState by appViewModel.appUiState.collectAsState()

            //external state
            val externalState = rememberExternalState(
                windowSizeClass = calculateWindowSizeClass(),
                internetEnabled = internetEnabled
            )

            //get app theme
            val isDarkAppTheme = when (appUiState.appPreferences.theme.appTheme) {
                AppTheme.LIGHT -> false
                AppTheme.DARK -> true
                AppTheme.AUTO -> isSystemInDarkTheme()
            }


            BannerItTheme(darkTheme = isDarkAppTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BannerItApp(
                        externalState = externalState,
                        appViewModel = appViewModel,
                        isDarkAppTheme = isDarkAppTheme
                    )
                }
            }
        }
    }
}
