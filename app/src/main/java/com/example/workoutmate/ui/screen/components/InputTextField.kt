package com.example.workoutmate.ui.screen.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    singleLine: Boolean = true,
    verticalPadding: Dp = 12.dp,
    horizontalPadding: Dp = 12.dp,
    onValueChange: (String) -> Unit,
    trailingContent: (@Composable () -> Unit)? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    labelStyle: TextStyle = MaterialTheme.typography.bodySmall,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        cursorColor = DarkGreen,
        focusedLabelColor = DarkGreen,
        unfocusedLabelColor = DarkGray,
        focusedBorderColor = DarkGreen,
        unfocusedBorderColor = LightGray
    )
) {
    val interactionSource = remember { MutableInteractionSource() }

    val state = rememberTextFieldState(initialText = value)

    val effectiveKeyboardOptions = if (singleLine) keyboardOptions.copy(imeAction = ImeAction.Done)
    else keyboardOptions

    LaunchedEffect(value) {
        if (state.text.toString() != value) {
            state.setTextAndPlaceCursorAtEnd(value)
        }
    }

    LaunchedEffect(state, singleLine, value) {
        snapshotFlow { state.text.toString() }.collect { newText ->
            val cleaned = if (singleLine) newText.replace("\n", "") else newText
            if (cleaned != value) onValueChange(cleaned)
            if (singleLine && cleaned != newText) state.setTextAndPlaceCursorAtEnd(cleaned)
        }
    }

    BasicTextField(
        state = state,
        enabled = enabled,
        modifier = modifier,
        textStyle = textStyle,
        interactionSource = interactionSource,
        keyboardOptions = effectiveKeyboardOptions,
        decorator = { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                colors = colors,
                isError = isError,
                enabled = enabled,
                singleLine = singleLine,
                value = state.text.toString(),
                innerTextField = innerTextField,
                trailingIcon = trailingContent,
                interactionSource = interactionSource,
                visualTransformation = VisualTransformation.None,
                label = { Text(label, style = labelStyle) },
                contentPadding = PaddingValues(
                    horizontal = horizontalPadding, vertical = verticalPadding
                ),
                container = {
                    OutlinedTextFieldDefaults.Container(
                        enabled = enabled,
                        isError = isError,
                        interactionSource = interactionSource,
                        colors = colors,
                        shape = ShapeDefaults.Small
                    )
                })
        })
}