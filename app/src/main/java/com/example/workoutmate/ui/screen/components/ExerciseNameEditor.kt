package com.example.workoutmate.ui.screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen

@Composable
fun ExerciseNameEditor(
    exerciseName: String,
    cancelEdit: () -> Unit,
    onSave: (String) -> Unit,
    label: String = "Exercise name"
) {
    var newName by remember { mutableStateOf(exerciseName) }

    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        InputTextField(
            label = label,
            value = newName,
            verticalPadding = 4.dp,
            horizontalPadding = 8.dp,
            onValueChange = { newName = it },
            modifier = Modifier.weight(1f)
        )

        IconButton(
            enabled = newName.isNotEmpty(),
            onClick = { onSave(newName.trim()) },
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                Icons.Outlined.Check,
                contentDescription = "Save",
                modifier = Modifier.size(20.dp),
                tint = if (newName.isNotEmpty()) DarkGreen else DarkGray
            )
        }

        IconButton(
            onClick = cancelEdit, modifier = Modifier.size(28.dp)
        ) {
            Icon(
                Icons.Outlined.Close,
                tint = DarkGray,
                contentDescription = "Cancel",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}