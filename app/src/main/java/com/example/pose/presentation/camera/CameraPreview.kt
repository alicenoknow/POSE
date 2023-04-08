package com.example.pose.presentation.camera

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pose.presentation.MainScreenViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraPreview(
    viewModel: MainScreenViewModel = hiltViewModel(),
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
) {
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val motionDetector = MotionDetection(onMotionStopped = { viewModel.isFrameStill = true })

    AndroidView(
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    this.scaleType = scaleType
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }

                val previewUseCase = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                viewModel.imageAnalysis.setAnalyzer(
                    context.executor
                ) { imageProxy ->
                    val bitmap = previewView.bitmap
                    if (bitmap != null) {
                        motionDetector.detectMotion(bitmap)
                        viewModel.captureFrame(context)
                        bitmap.recycle()
                    }
                    imageProxy.close()
                }

                coroutineScope.launch {
                    val cameraProvider = context.getCameraProvider()

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            previewUseCase,
                            viewModel.imageCapture,
                            viewModel.imageAnalysis

                        )
                    } catch (ex: Exception) {
                        Log.e("CameraPreview", "Binding failed", ex)
                    }
                }
                previewView
            })
}

