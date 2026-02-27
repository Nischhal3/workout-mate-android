package com.example.workoutmate.ui.screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedErrorText(
    visible: Boolean,
    message: String,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp
) {
    AnimatedVisibility(
        visible = visible && message.isNotBlank(),
        modifier = modifier,
        enter = fadeIn() + slideInVertically { it / 2 },
        exit = fadeOut() + slideOutVertically { it / 2 }
    ) {
        Text(
            text = message,
            color = Color.Red,
            fontSize = fontSize,
            modifier = textModifier
        )
    }
}