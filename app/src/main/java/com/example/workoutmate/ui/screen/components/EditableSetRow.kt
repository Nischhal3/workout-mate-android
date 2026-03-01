package com.example.workoutmate.ui.screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.LightGray
import com.example.workoutmate.utils.validateWorkoutFormInput

@Composable
fun EditableSetRow(
    setLabel: String,
    initialReps: Int,
    isEditing: Boolean,
    displayText: String,
    initialWeightKg: Double,
    onEditClick: () -> Unit,
    onCancelEdit: () -> Unit,
    displayTextFontSize: TextUnit = 16.sp,
    onSave: (weight: Double, reps: Int) -> Unit,
    trailingActions: @Composable RowScope.() -> Unit,
) {
    if (isEditing) SetEditor(
        onSave = onSave,
        cancelEdit = onCancelEdit,
        initialReps = initialReps,
        initialWeightKg = initialWeightKg
    )
    else SetDisplayRow(
        setLabel = setLabel,
        displayText = displayText,
        onEditClick = onEditClick,
        trailingActions = trailingActions,
        displayTextFontSize = displayTextFontSize,
    )
}

@Composable
private fun SetEditor(
    initialReps: Int,
    cancelEdit: () -> Unit,
    initialWeightKg: Double,
    repsLabel: String = "Reps",
    weightLabel: String = "Weight",
    onSave: (weightKg: Double, reps: Int) -> Unit
) {
    var newReps by remember { mutableStateOf(initialReps.toString()) }
    var newWeight by remember { mutableStateOf(initialWeightKg.toString()) }

    val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

    val validation = remember(newReps, newWeight) {
        validateWorkoutFormInput(newReps, newWeight)
    }

    val hasError = validation.hasError
    val errorMessage = validation.errorMessage
    val canSave = !hasError && newReps.isNotEmpty() && newWeight.isNotEmpty()

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f)) {
                InputTextField(
                    label = weightLabel,
                    value = newWeight,
                    verticalPadding = 4.dp,
                    horizontalPadding = 8.dp,
                    keyboardOptions = keyboardOptions,
                    onValueChange = { newWeight = it },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                InputTextField(
                    label = repsLabel,
                    value = newReps,
                    verticalPadding = 4.dp,
                    horizontalPadding = 8.dp,
                    onValueChange = { newReps = it },
                    keyboardOptions = keyboardOptions,
                    modifier = Modifier.weight(1f)
                )
            }

            IconButton(
                enabled = canSave,
                modifier = Modifier.size(28.dp),
                onClick = { onSave(newWeight.toDouble(), newReps.toInt()) }) {
                Icon(
                    contentDescription = "Save",
                    imageVector = Icons.Outlined.Check,
                    modifier = Modifier.size(20.dp),
                    tint = if (canSave) DarkGreen else DarkGray
                )
            }

            IconButton(
                onClick = cancelEdit, modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    tint = DarkGray,
                    contentDescription = "Cancel",
                    imageVector = Icons.Outlined.Close,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        AnimatedErrorText(
            visible = hasError,
            message = errorMessage,
            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
        )
    }
}

@Composable
private fun SetDisplayRow(
    setLabel: String,
    displayText: String,
    onEditClick: () -> Unit,
    displayTextFontSize: TextUnit,
    trailingActions: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, LightGray, RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = setLabel,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier.weight(2f), contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = displayText,
                style = MaterialTheme.typography.bodySmall,
                fontSize = displayTextFontSize
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onEditClick, modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit set",
                    tint = DarkGray,
                    modifier = Modifier.size(22.dp)
                )
            }
            trailingActions()
        }
    }
}