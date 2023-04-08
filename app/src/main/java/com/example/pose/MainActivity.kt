package com.example.pose

import com.example.pose.presentation.camera.CameraScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pose.navigation.Screens
import com.example.pose.presentation.MainScreen
import com.example.pose.presentation.MainScreenViewModel
import com.example.pose.ui.theme.POSETheme
import com.example.pose.ui.theme.Primary
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            POSETheme {
                Surface(
//                    modifier = Modifier.fillMaxSize(),
                    color = Primary
                ) {
                    val vm: MainScreenViewModel by viewModels()
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Main.route
                    ) {
                        composable(
                            route = Screens.Main.route
                        ) {
                            MainScreen(navController, vm)
                        }
                        composable(
                            route = Screens.Camera.route
                        ) {
                            CameraScreen(vm)
                        }
                    }

                }
            }
        }
    }
}
