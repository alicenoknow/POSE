package com.example.pose.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pose.ui.theme.JostFontFamily
import com.example.pose.ui.theme.Primary
import com.example.pose.ui.theme.Secondary
import com.example.pose.ui.theme.SecondaryAlpha

@Composable
fun TakePicButton(label: String = "Start", enabled: Boolean = true, onClick: () -> Unit) {
    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Secondary,
            disabledBackgroundColor = SecondaryAlpha
        ),
        modifier = Modifier.width(300.dp),
        enabled = enabled,
    ) {
        Text(label,
            fontFamily = JostFontFamily,
            fontSize = 32.sp,
            color = Primary,
            fontWeight = FontWeight.Bold)
    }
}
