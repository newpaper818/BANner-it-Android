package com.fitfit.feature.report.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.ExifInterface
import android.util.Log
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.FileOutputStream
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors
import javax.inject.Inject

private const val CAMERA_PREVIEW_VIEWMODEL_TAG = "Camera-Preview-ViewModel"

data class CameraPreviewUiState(
    val isTakingPhoto: Boolean = false,
    val cameraAspectRatio: Float = 4f / 3f
)

@HiltViewModel
class CameraPreviewViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _cameraPreviewUiState: MutableStateFlow<CameraPreviewUiState> =
        MutableStateFlow(CameraPreviewUiState())
    val cameraPreviewUiState = _cameraPreviewUiState.asStateFlow()

    // used to set up a link between the Camera and your UI.
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest

    private val _imageCapture = MutableStateFlow<ImageCapture?>(null)
    val imageCapture: StateFlow<ImageCapture?> = _imageCapture



    init {
        getCameraAspectRatio()
    }

    private val cameraPreviewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { newSurfaceRequest ->
            _surfaceRequest.update { newSurfaceRequest }
        }
    }

    suspend fun bindToCamera(appContext: Context, lifecycleOwner: LifecycleOwner) {
        _imageCapture.value = ImageCapture.Builder().build()
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)
        processCameraProvider.bindToLifecycle(
            lifecycleOwner, DEFAULT_BACK_CAMERA, cameraPreviewUseCase, imageCapture.value
        )

        // Cancellation signals we're done with the camera
        try { awaitCancellation() } finally { processCameraProvider.unbindAll() }
    }

    private fun getCameraAspectRatio() {
        val cameraManager = getApplication(context)
            .getSystemService(Context.CAMERA_SERVICE) as CameraManager

        try {
            val cameraId = cameraManager.cameraIdList.first()
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)
            val size = characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE)

            size?.let {
                val ratio = it.height.toFloat() / it.width.toFloat()
                setCameraAspectRatio(ratio)
            }
        } catch (e: Exception) {
            Log.e(CAMERA_PREVIEW_VIEWMODEL_TAG, "Error getting camera aspect ratio", e)
        }
    }



    fun setIsTakingPhoto(isTakingPhoto: Boolean) {
        _cameraPreviewUiState.update {
            it.copy(isTakingPhoto = isTakingPhoto)
        }
    }

    fun setCameraAspectRatio(cameraAspectRatio: Float) {
        _cameraPreviewUiState.update {
            it.copy(cameraAspectRatio = cameraAspectRatio)
        }
    }


    fun takePhoto(
        userId: Int,
        onPhotoSaved: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        setIsTakingPhoto(true)

        val imageCapture = imageCapture ?: return

        val now = ZonedDateTime.now(ZoneId.of("UTC"))
        val dateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"))

        val photoFile = File(
            context.filesDir,
            "${userId}_${dateTime}_0.jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        val executor = Executors.newSingleThreadExecutor()

        imageCapture.value?.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    try {
                        compressAndRotateImage(photoFile)
                        Log.d(CAMERA_PREVIEW_VIEWMODEL_TAG, "take photo: ${photoFile.name}")
                        onPhotoSaved(photoFile.name)
                    } catch (e: Exception){
                        Log.e(CAMERA_PREVIEW_VIEWMODEL_TAG, "Error compressing photo", e)
                        onError(e)
                    }
                }

                override fun onError(e: ImageCaptureException) {
                    Log.e(CAMERA_PREVIEW_VIEWMODEL_TAG, "Error taking photo", e)
                    onError(e)
                }
            }
        )
    }







    private fun compressImage(
        photoFile: File,
        quality: Int = 25
    ){
        val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath) ?: return
        val outputStream = FileOutputStream(photoFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        outputStream.flush()
        outputStream.close()
    }

    private fun compressAndRotateImage(
        photoFile: File,
        quality: Int = 80
    ) {
        val exif = ExifInterface(photoFile.absolutePath)
        val rotation = when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }
        Log.d(CAMERA_PREVIEW_VIEWMODEL_TAG, "rotation: $rotation")

        val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath) ?: return
        val matrix = Matrix().apply { postRotate(rotation.toFloat()) }
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        // save rotated bitmap to file
        val outputStream = FileOutputStream(photoFile)
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        outputStream.flush()
        outputStream.close()

        bitmap.recycle()
        rotatedBitmap.recycle()
    }
}