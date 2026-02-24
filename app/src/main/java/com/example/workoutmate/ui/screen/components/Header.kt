package com.example.workoutmate.ui.screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.workoutmate.ui.theme.Green
import com.example.workoutmate.ui.theme.LightGray

@Composable
fun Header(
    title: String,
    leftIcon: ImageVector,
    rightIcon: ImageVector,
    onLeftClick: (() -> Unit),
    onRightClick: (() -> Unit),
    leftIconTint: Color = Green,
    rightIconTint: Color = Green,
    modifier: Modifier = Modifier,
    rightIconEnabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,

    ) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth()
    ) {

        IconButton(
            onClick = onLeftClick, modifier = Modifier.size(40.dp)
        ) {
            Icon(
                tint = leftIconTint,
                imageVector = leftIcon,
                contentDescription = "Left Action",
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = title,
            style = textStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        IconButton(
            enabled = rightIconEnabled, onClick = onRightClick, modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = rightIcon,
                contentDescription = "Right Action",
                modifier = Modifier.size(20.dp),
                tint = if (rightIconEnabled) rightIconTint else LightGray
            )
        }
    }
}