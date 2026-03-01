package com.example.workoutmate.ui.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.LightGray

@Composable
fun CustomIcon(
    iconTint: Color,
    icon: ImageVector,
    onClick: () -> Unit,
    enabled: Boolean = true,
    contentDescription: String,
    useCircularBackground: Boolean,
) {
    IconButton(
        enabled = enabled, onClick = onClick, modifier = Modifier.size(40.dp)
    ) {
        if (useCircularBackground) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = if (enabled) DarkGreen.copy(alpha = 0.85f) else LightGray.copy(alpha = 0.35f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = icon,
                    modifier = Modifier.size(24.dp),
                    contentDescription = contentDescription,
                    tint = if (enabled) iconTint else DarkGreen.copy(alpha = 0.25f)
                )
            }
        } else {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = if (enabled) iconTint else LightGray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}