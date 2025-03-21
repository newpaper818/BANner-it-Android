package com.fitfit.feature.report.camera

import android.app.Application
import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.Log
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val CAMERA_PREVIEW_VIEWMODEL_TAG = "Camera-Preview-ViewModel"

@HiltViewModel
class CameraPreviewViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    // used to set up a link between the Camera and your UI.
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest

    private val _cameraAspectRatio = MutableStateFlow(4f / 3f) // 기본값 설정
    val cameraAspectRatio: StateFlow<Float> = _cameraAspectRatio


    init {
        getCameraAspectRatio()
    }

    private val cameraPreviewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { newSurfaceRequest ->
            _surfaceRequest.update { newSurfaceRequest }
        }
    }

    suspend fun bindToCamera(appContext: Context, lifecycleOwner: LifecycleOwner) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)
        processCameraProvider.bindToLifecycle(
            lifecycleOwner, DEFAULT_BACK_CAMERA, cameraPreviewUseCase
        )

        // Cancellation signals we're done with the camera
        try { awaitCancellation() } finally { processCameraProvider.unbindAll() }
    }

    private fun getCameraAspectRatio() {
        val cameraManager = getApplication(context)
            .getSystemService(Context.CAMERA_SERVICE) as CameraManager

        try {
            val cameraId = cameraManager.cameraIdList.first() // 기본 후면 카메라 사용
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)
            val size = characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE)

            size?.let {
                val ratio = it.height.toFloat() / it.width.toFloat() // 동적 비율 계산
                _cameraAspectRatio.value = ratio
            }
        } catch (e: Exception) {
            Log.e(CAMERA_PREVIEW_VIEWMODEL_TAG, "Error getting camera aspect ratio", e)
        }
    }
}