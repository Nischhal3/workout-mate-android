package com.example.workoutmate.ui.screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.workoutmate.ui.theme.Green
import com.example.workoutmate.ui.theme.White

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    border: BorderStroke? = null,
    textAlign: TextAlign = TextAlign.Center,
    leading: (@Composable (() -> Unit))? = null,
    trailing: (@Composable (() -> Unit))? = null,
    shape: Shape = RoundedCornerShape(12.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Green, contentColor = White
    ),
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),

    ) {
    Button(
        shape = shape,
        colors = colors,
        border = border,
        onClick = onClick,
        modifier = modifier,
        elevation = elevation,
        enabled = enabled && !loading,
        contentPadding = contentPadding
    ) {
        if (leading != null) {
            leading()
            Spacer(Modifier.width(8.dp))
        }

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp), strokeWidth = 2.dp
            )
            Spacer(Modifier.width(10.dp))
        }

        Text(
            text = text, style = textStyle, textAlign = textAlign
        )

        if (trailing != null) {
            Spacer(Modifier.width(8.dp))
            trailing()
        }
    }
}