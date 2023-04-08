package com.example.pose.presentation

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.pose.presentation.camera.executor
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainScreenViewModel() : ViewModel() {
    var delay by mutableStateOf(0)
        private set
    var isFrameStill by mutableStateOf(false)
    var startTimestamp by mutableStateOf(0L)
    var photoTaken by mutableStateOf(false)

    fun setNewDelay(value: Int) {
        if (value >= 0) {
            delay = value
        }
    }

    fun reset() {
        delay = 0
        isFrameStill = false
    }

    fun isTakePhotoEnabled(): Boolean { return startTimestamp == 0L || photoTaken }

    fun getLabel(): String {
        if (startTimestamp == 0L)
            return "Take a pic"
        else if (photoTaken)
            return "Again?"
        return "Time to POSE!"
    }

    val imageAnalysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()

    val imageCapture = ImageCapture.Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
        .build()

    fun captureFrame (context: Context) {
        if (startTimestamp != 0L && System.currentTimeMillis() - startTimestamp > delay * 1000) {
            if (isFrameStill && !photoTaken) {
                val name = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
                    .format(System.currentTimeMillis())
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                        val appName = "POSE"
                        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/${appName}")
                    }
                }
                val outputOptions = ImageCapture.OutputFileOptions
                    .Builder(
                        context.contentResolver,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                    )
                    .build()

                imageCapture.takePicture(
                    outputOptions,
                    context.executor,
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onError(exc: ImageCaptureException) {
                            Log.e("Image capture", "Photo capture failed: ${exc.message}", exc)
                        }

                        override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                            val savedUri = output.savedUri
                        }
                    })

                photoTaken = true
            }
        }
    }

    suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { future ->
            future.addListener(
                {
                    continuation.resume(future.get())
                },
                executor
            )
        }
    }

    val Context.executor: Executor
        get() = ContextCompat.getMainExecutor(this)
}