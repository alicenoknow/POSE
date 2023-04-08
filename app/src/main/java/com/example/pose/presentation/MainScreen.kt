package com.example.pose.presentation

import com.example.pose.R
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.Image
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pose.presentation.components.Counter
import com.example.pose.presentation.components.TakePicButton
import com.example.pose.ui.theme.Secondary


@Composable
fun MainScreen(navController: NavController, viewModel: MainScreenViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.startTimestamp = 0L
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            val image: Painter = painterResource(id = R.drawable.logo)

            Image(painter = image, contentDescription = "")
            Text("Don't race the self-timer.\nTake your time and POSE.", color = Secondary)

            Spacer(modifier = Modifier.size(120.dp))
            Counter(viewModel.delay, setCounter = { value -> viewModel.setNewDelay(value) })
            Spacer(modifier = Modifier.size(120.dp))

            Text("POSE will take a photo\nwhen you stop moving.", color = Secondary)

            Spacer(modifier = Modifier.size(24.dp))
            TakePicButton(onClick = { navController.navigate("camera_screen") })
        }
    }
}