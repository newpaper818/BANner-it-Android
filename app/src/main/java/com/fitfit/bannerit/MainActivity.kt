package com.fitfit.bannerit

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
import com.fitfit.core.model.enums.AppTheme
import com.fitfit.core.ui.designsystem.theme.BannerItTheme
import com.fitfit.bannerit.ui.AppViewModel
import com.fitfit.bannerit.ui.BannerItApp
import com.fitfit.bannerit.ui.rememberExternalState
import com.fitfit.bannerit.utils.ConnectivityObserver
import com.fitfit.bannerit.utils.NetworkConnectivityObserver
import com.fitfit.bannerit.utils.calculateWindowSizeClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val MAIN_ACTIVITY_TAG = "MainActivity1"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val appViewModel: AppViewModel by viewModels()

    private lateinit var connectivityObserver: ConnectivityObserver


    override fun onCreate(savedInstanceState: Bundle?) {

        //splash screen ----------------------------------------------------------------------------------------
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            appViewModel.appUiState.value.screenDestination.startScreenDestination == null
        }

        //get signed user and update start destination ---------------------------------------------------------
        appViewModel.viewModelScope.launch {

            //get signed user and update start destination
            Log.d(MAIN_ACTIVITY_TAG, "- init user and update start destination start")

            //this function will get user and set {appViewModel.appUiState.value.screenDestination.startScreenDestination}
            appViewModel.intiUserAndUpdateStartDestination()
        }


        enableEdgeToEdge()

        //connectivityObserver
        connectivityObserver = NetworkConnectivityObserver(applicationContext)



        // ----------------------------------------------------------------------------------------
        setContent {
            val appUiState by appViewModel.appUiState.collectAsState()

            //external state
            val externalState = rememberExternalState(
                context = applicationContext,
                windowSizeClass = calculateWindowSizeClass(),
                connectivityObserver = connectivityObserver
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
