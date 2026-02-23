package com.example.workoutmate.ui.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun VerticalScrollbar(
    width: Dp = 6.dp,
    listState: LazyListState,
    minThumbHeight: Dp = 24.dp,
    modifier: Modifier = Modifier,
    thumbColor: Color = Color.DarkGray,
    trackColor: Color = Color.LightGray.copy(alpha = 0.5f)
) {
    val density = LocalDensity.current
    var containerHeightPx by remember { mutableFloatStateOf(0f) }
    var thumbOffsetPx by remember { mutableFloatStateOf(0f) }
    var thumbHeightPx by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }.collect { layoutInfo ->
            val totalItems = layoutInfo.totalItemsCount
            val visibleItems = layoutInfo.visibleItemsInfo.size
            if (totalItems <= visibleItems || containerHeightPx == 0f) {
                thumbHeightPx = 0f
                thumbOffsetPx = 0f
                return@collect
            }

            thumbHeightPx =
                (containerHeightPx * visibleItems / totalItems).coerceAtLeast(with(density) { minThumbHeight.toPx() })

            val firstVisibleIndex = listState.firstVisibleItemIndex
            val scrollProgress = firstVisibleIndex / (totalItems - visibleItems).toFloat()
            thumbOffsetPx = (containerHeightPx - thumbHeightPx) * scrollProgress
        }
    }

    Box(
        modifier = modifier
            .fillMaxHeight()
            .onGloballyPositioned { coordinates ->
                containerHeightPx = coordinates.size.height.toFloat()
            }) {
        if (thumbHeightPx > 0f && containerHeightPx > 0f) {
            Box(
                modifier = Modifier
                    .width(width)
                    .fillMaxHeight()
                    .background(trackColor)
                    .align(Alignment.CenterEnd)
            )

            Box(
                modifier = Modifier
                    .width(width)
                    .align(Alignment.TopEnd)
                    .offset { IntOffset(0, thumbOffsetPx.toInt()) }
                    .background(thumbColor, RoundedCornerShape(3.dp))
                    .height(with(density) { thumbHeightPx.toDp() })
            )
        }
    }
}