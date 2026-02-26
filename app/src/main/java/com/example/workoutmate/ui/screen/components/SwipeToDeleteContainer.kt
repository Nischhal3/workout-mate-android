package com.example.workoutmate.ui.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.workoutmate.ui.theme.Red
import kotlinx.coroutines.launch

@Composable
fun SwipeToDeleteContainer(
    modifier: Modifier = Modifier,
    enableStartToEnd: Boolean = true,
    enableEndToStart: Boolean = true,
    onDelete: (onError: () -> Unit) -> Unit,
    shape: Shape = RoundedCornerShape(16.dp),
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val dismissState = rememberSwipeToDismissBoxState(
        positionalThreshold = { fullWidth -> fullWidth * 0.85f }
    )

    LaunchedEffect(dismissState.currentValue) {
        val dismissed =
            dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd ||
                    dismissState.currentValue == SwipeToDismissBoxValue.EndToStart

        if (dismissed) {
            onDelete {
                scope.launch { dismissState.reset() }
            }
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = enableStartToEnd,
        enableDismissFromEndToStart = enableEndToStart,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape)
                    .background(Red.copy(alpha = 0.50f))
            )
        },
        modifier = modifier.clip(shape)
    ) {
        content()
    }
}