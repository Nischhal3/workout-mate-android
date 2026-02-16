package com.example.workoutmate.ui.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun FlipCard(
    rotationY: Float, front: @Composable () -> Unit, back: @Composable () -> Unit
) {
    val isFront = rotationY <= 90f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 210.dp)
            .graphicsLayer {
                this.rotationY = rotationY
                cameraDistance = 12f * density
            },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), contentAlignment = Alignment.TopStart
        ) {
            if (isFront) {
                key("create") { front() }
            } else {
                Box(modifier = Modifier.graphicsLayer { this@graphicsLayer.rotationY = 180f }) {
                    key("login") { back() }
                }
            }
        }
    }
}