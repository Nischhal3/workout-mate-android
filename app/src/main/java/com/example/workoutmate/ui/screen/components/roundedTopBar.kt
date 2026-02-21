package com.example.workoutmate.ui.screen.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

fun Modifier.roundedTopBar(
    strokeWidth: Dp, leftColor: Color, rightColor: Color, radius: Dp
) = composed {

    val strokePx = with(LocalDensity.current) { strokeWidth.toPx() }
    val radiusPx = with(LocalDensity.current) { radius.toPx() }

    this.then(
        Modifier.drawBehind {

            val mid = size.width / 2f

            // Left Half
            val leftPath = Path().apply {
                moveTo(radiusPx, 0f)
                lineTo(mid, 0f)

                arcTo(
                    rect = Rect(
                        0f, 0f, 2 * radiusPx, 2 * radiusPx
                    ), startAngleDegrees = 180f, sweepAngleDegrees = 90f, forceMoveTo = false
                )
            }

            drawPath(
                path = leftPath, color = leftColor, style = Stroke(strokePx)
            )

            // Right Half
            val rightPath = Path().apply {
                moveTo(mid, 0f)
                lineTo(size.width - radiusPx, 0f)

                arcTo(
                    rect = Rect(
                        size.width - 2 * radiusPx, 0f, size.width, 2 * radiusPx
                    ), startAngleDegrees = -90f, sweepAngleDegrees = 90f, forceMoveTo = false
                )
            }

            drawPath(
                path = rightPath, color = rightColor, style = Stroke(strokePx)
            )
        })
}