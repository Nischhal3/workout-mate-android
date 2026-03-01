package com.example.workoutmate.ui.screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.workoutmate.ui.theme.Green

@Composable
fun Header(
    title: String,
    leftIcon: ImageVector,
    rightIcon: ImageVector,
    modifier: Modifier = Modifier,
    leftIconTint: Color = Green,
    rightIconTint: Color = Green,
    onLeftIconClick: (() -> Unit),
    onRightIconClick: (() -> Unit),
    rightIconEnabled: Boolean = true,
    useCircularBackground: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth()
    ) {
        CustomIcon(
            icon = leftIcon,
            iconTint = leftIconTint,
            onClick = onLeftIconClick,
            contentDescription = "Left Action",
            useCircularBackground = useCircularBackground
        )
        Text(
            text = title,
            style = textStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        CustomIcon(
            icon = rightIcon,
            iconTint = rightIconTint,
            onClick = onRightIconClick,
            enabled = rightIconEnabled,
            contentDescription = "Right Action",
            useCircularBackground = useCircularBackground
        )
    }
}
