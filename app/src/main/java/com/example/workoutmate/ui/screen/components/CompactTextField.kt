package com.example.workoutmate.ui.screen.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactTextField(
    value: String,
    label: String,
    verticalPadding: Dp = 4.dp,
    horizontalPadding: Dp = 8.dp,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    trailingContent: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxSize()
            ) {
                TextFieldDefaults.DecorationBox(
                    value = value,
                    enabled = true,
                    singleLine = true,
                    innerTextField = innerTextField,
                    trailingIcon = trailingContent,
                    interactionSource = interactionSource,
                    visualTransformation = visualTransformation,
                    label = { Text(label, color = DarkGreen) },
                    colors = outlinedTextFieldColors(
                        cursorColor = DarkGreen,
                        focusedLabelColor = DarkGreen,
                        unfocusedLabelColor = DarkGray,
                        focusedBorderColor = DarkGreen,
                        unfocusedBorderColor = LightGray,
                    ),
                    contentPadding = PaddingValues(
                        vertical = verticalPadding, horizontal = horizontalPadding
                    )
                )
            }
        })
}