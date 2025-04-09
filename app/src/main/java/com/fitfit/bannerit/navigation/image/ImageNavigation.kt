package com.fitfit.bannerit.navigation.image

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fitfit.bannerit.navigation.TopLevelDestination
import com.fitfit.bannerit.navigation.imageEnterTransition
import com.fitfit.bannerit.navigation.imageExitTransition
import com.fitfit.bannerit.navigation.imagePopEnterTransition
import com.fitfit.bannerit.navigation.imagePopExitTransition
import com.fitfit.bannerit.ui.AppViewModel
import com.fitfit.bannerit.ui.CommonReportRecordsViewModel
import com.fitfit.bannerit.ui.ExternalState
import com.fitfit.core.model.enums.ScreenDestination
import com.fitfit.feature.image.image.ImageRoute

private val topLevelScreenDestination = TopLevelDestination.MORE
private val screenDestination = ScreenDestination.IMAGE

fun NavController.navigateToImage(navOptions: NavOptions? = null) =
    navigate(screenDestination.route, navOptions)

fun NavGraphBuilder.imageScreen(
    appViewModel: AppViewModel,
    commonReportRecordsViewModel: CommonReportRecordsViewModel,
    externalState: ExternalState,

    navigateUp: () -> Unit,
){
    composable(
        route = screenDestination.route,
        enterTransition = { imageEnterTransition },
        exitTransition = { imageExitTransition },
        popEnterTransition = { imagePopEnterTransition },
        popExitTransition = { imagePopExitTransition }
    ) {
        LaunchedEffect(Unit) {
            appViewModel.updateCurrentScreenDestination(screenDestination)
        }

        val commonReportUiState by commonReportRecordsViewModel.reportUiState.collectAsState()

        ImageRoute(
            imageUerId = 1,
            internetEnabled = externalState.internetEnabled,
            images = commonReportUiState.images,
            initialImageIndex = commonReportUiState.initialImageIndex,
            navigateUp = navigateUp,
            downloadImage = {_,_,_ ->},
            saveImageToExternalStorage = { _ -> true }
        )
    }
}