package com.example.pose.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pose.ui.theme.Secondary

@Composable
fun Counter(counter: Int, setCounter: (value: Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Initial delay:", fontWeight = FontWeight.Bold, color = Secondary)
        Spacer(modifier = Modifier.size(20.dp))
        Text(counter.toString() + "s", color = Secondary)
        Spacer(modifier = Modifier.size(20.dp))

        Column() {
            UpperTriangleButton (onClick = { setCounter(counter + 1) })
            Spacer(modifier = Modifier.size(20.dp))
            LowerTriangleButton(onClick = { setCounter(counter - 1) })

        }
    }
}