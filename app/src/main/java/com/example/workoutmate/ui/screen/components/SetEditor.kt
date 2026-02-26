package com.example.workoutmate.ui.screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen

@Composable
fun SetEditor(
    initialReps: Int,
    cancelEdit: () -> Unit,
    initialWeightKg: Double,
    repsLabel: String = "Reps",
    weightLabel: String = "Weight",
    onSave: (weightKg: Double, reps: Int) -> Unit
) {
    var newReps by remember { mutableStateOf(initialReps.toString()) }
    var newWeight by remember { mutableStateOf(initialWeightKg.toString()) }

    val reps = newReps.toIntOrNull()
    val weight = newWeight.toDoubleOrNull()
    val canSave = weight != null && weight >= 0.0 && reps != null && reps > 0
    val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

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
            onClick = { onSave(weight!!, reps!!) }) {
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
}