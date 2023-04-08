package com.example.pose.navigation

sealed class Screens(val route: String) {
    object Main: Screens("main_screen")
    object Camera: Screens("camera_screen")
}