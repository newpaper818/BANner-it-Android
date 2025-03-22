package com.fitfit.feature.report.camera

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitfit.core.ui.designsystem.components.button.CloseButton
import com.fitfit.core.ui.designsystem.components.button.ShutterButton
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.feature.report.camera.component.CameraPermissionNotGrantedCard
import com.fitfit.feature.report.camera.component.CameraPreviewContent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun CameraRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    cameraPreviewViewModel: CameraPreviewViewModel = hiltViewModel(),
){
    val activity = LocalActivity.current

    LaunchedEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    DisposableEffect(Unit) {
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    val imageCapture by cameraPreviewViewModel.imageCapture.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ){

        Column {
            Spacer(modifier = Modifier.weight(1f))

            CameraScreen(
                modifier = modifier,
                cameraPreviewViewModel = cameraPreviewViewModel
            )

            Spacer(modifier = Modifier.weight(1f))

            //buttons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .displayCutoutPadding()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(bottom = 32.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))

                //close button
                CloseButton(
                    onClick = navigateUp
                )

                Spacer(modifier = Modifier.weight(1f))

                //shutter button
                ShutterButton(
                    enabled = imageCapture != null,
                    onClick = {
                        cameraPreviewViewModel.takePhoto(
                            onPhotoSaved = { },
                            onError = { }
                        )
                    }
                )

                Spacer(modifier = Modifier.weight(1f))
                MySpacerRow(50.dp)
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CameraScreen(
    modifier: Modifier = Modifier,
    cameraPreviewViewModel: CameraPreviewViewModel,
){
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted){
            cameraPermissionState.launchPermissionRequest()
        }
    }

    val cameraAspectRatio by cameraPreviewViewModel.cameraAspectRatio.collectAsState()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(cameraAspectRatio),
        contentAlignment = Alignment.Center
    ) {

        //camera
        AnimatedVisibility(
            visible = cameraPermissionState.status.isGranted,
            enter = fadeIn(tween(500)),
            exit = fadeOut(tween(500))
        ) {
            CameraPreviewContent(
                cameraPreviewViewModel = cameraPreviewViewModel
            )
        }

        //check permission text
        AnimatedVisibility(
            visible = !cameraPermissionState.status.isGranted,
            enter = fadeIn(tween(500)),
            exit = fadeOut(tween(500))
        ) {
            CameraPermissionNotGrantedCard(
                onClickSettingsButton = {
                    //go to app settings
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    )
                    context.startActivity(intent)
                }
            )
        }
    }
}