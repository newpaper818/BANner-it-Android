package com.fitfit.bannerit.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.fitfit.core.model.enums.ScreenDestination
import com.fitfit.bannerit.navigation.BannerItNavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BannerItApp(
    externalState: ExternalState,
    appViewModel: AppViewModel,
//    tripsViewModel: TripsViewModel,
    isDarkAppTheme: Boolean,

    modifier: Modifier = Modifier,
) {

    val appUiState by appViewModel.appUiState.collectAsState()

    //system ui controller for status bar icon color
    val systemUiController = rememberSystemUiController()

    //set status bar color
    when (appUiState.screenDestination.currentScreenDestination) {
        //image
        ScreenDestination.CAMERA,
        ScreenDestination.IMAGE -> {
            systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = false)
        }

//        //trip map
//        ScreenDestination.TRIP_MAP -> {
//            if (isDarkMapTheme)
//                systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = false)
//            else
//                systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = true)
//        }
//
        else -> {
            if (isDarkAppTheme)
                systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = false)
            else
                systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = true)
        }
    }


    //set navigation bar(recent, home, back) color
    when (appUiState.screenDestination.currentScreenDestination) {
        ScreenDestination.CAMERA,
        ScreenDestination.IMAGE -> systemUiController.setNavigationBarColor(color = Color.Transparent)
//
        //top level destinations
        ScreenDestination.MAIN_REPORT, ScreenDestination.MAIN_RECORDS, ScreenDestination.MAIN_MORE
         -> systemUiController.setNavigationBarColor(color = MaterialTheme.colorScheme.surfaceDim)

        else -> systemUiController.setNavigationBarColor(color = MaterialTheme.colorScheme.surface)
    }



    val startDestination = appUiState.screenDestination.startScreenDestination?.route

    if (startDestination != null){
        BannerItNavHost(
            externalState = externalState,
            appViewModel = appViewModel,

            isDarkAppTheme = isDarkAppTheme,
            startDestination = startDestination,
            modifier = modifier
        )
    }
}