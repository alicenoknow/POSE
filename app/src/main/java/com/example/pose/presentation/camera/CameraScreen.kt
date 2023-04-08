package com.example.pose.presentation.camera

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pose.presentation.MainScreenViewModel
import com.example.pose.presentation.camera.*
import com.example.pose.presentation.components.TakePicButton
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.util.*

fun getPermissions(): List<String> {
    return if (Build.VERSION.SDK_INT <= 28){
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    } else listOf(Manifest.permission.CAMERA)
}

@SuppressLint("UnsafeOptInUsageError", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(viewModel: MainScreenViewModel = hiltViewModel()) {
    DisposableEffect(key1 = viewModel) {
        onDispose { viewModel.reset() }
    }

    val configuration = LocalConfiguration.current
    val screeHeight = configuration.screenHeightDp.dp

    val permissionState = rememberMultiplePermissionsState(
        permissions = getPermissions()
    )

    if (!permissionState.allPermissionsGranted){
        SideEffect { permissionState.launchMultiplePermissionRequest() }
    }

    if (permissionState.allPermissionsGranted){
        Box(modifier = Modifier
            .fillMaxSize()
        ) {
            CameraPreview(viewModel)
            Box(modifier = Modifier
                .offset(y = screeHeight * 0.4f)
                .align(Alignment.Center)) {

                TakePicButton(label = viewModel.getLabel(), enabled = viewModel.isTakePhotoEnabled(),  onClick = {
                    viewModel.photoTaken = false
                    viewModel.startTimestamp = System.currentTimeMillis()
                })
            }
        }
    } else {
        Text("No permissions")
    }


}
