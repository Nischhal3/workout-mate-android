package com.example.workoutmate.ui.screen.dashboard.components.workoutform

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.workoutmate.ui.screen.components.CompactTextField
import com.example.workoutmate.ui.screen.components.InputTextField
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.Green
import com.example.workoutmate.ui.theme.LightSage
import com.example.workoutmate.ui.theme.White

data class ExerciseEntry(
    val weight: String, val reps: String
)

data class ExerciseSet(
    val name: String, val exercises: List<ExerciseEntry> = emptyList()
)

@Composable
fun AddExerciseDialog(
    onDismiss: () -> Unit, onAdd: (ExerciseSet) -> Unit
) {
    var setName by remember { mutableStateOf("") }
    var exerciseEntries by remember { mutableStateOf(listOf(ExerciseEntry("", ""))) }
    val scrollState = rememberScrollState()

    AlertDialog(onDismissRequest = onDismiss, title = {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text("Add Exercise Set", fontWeight = FontWeight.Bold, color = DarkGreen)
            }
            IconButton(onClick = {
                exerciseEntries = exerciseEntries + ExerciseEntry("", "")
            }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Exercise",
                    tint = DarkGreen,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }, text = {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 450.dp)
                .verticalScroll(scrollState)
        ) {
            InputTextField(
                value = setName, onValueChange = { setName = it }, label = "Set Name"
            )

            exerciseEntries.forEachIndexed { index, entry ->
                ExerciseEntryRow(entry = entry, onWeightChange = { newWeight ->
                    exerciseEntries = exerciseEntries.toMutableList()
                        .also { it[index] = it[index].copy(weight = newWeight) }
                }, onRepsChange = { newReps ->
                    exerciseEntries = exerciseEntries.toMutableList()
                        .also { it[index] = it[index].copy(reps = newReps) }
                }, onDelete = {
                    exerciseEntries = exerciseEntries.toMutableList().also { it.removeAt(index) }
                })
            }
        }
    }, confirmButton = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (setName.isNotBlank() && exerciseEntries.isNotEmpty()) {
                        onAdd(ExerciseSet(setName, exerciseEntries))
                        onDismiss()
                    }
                },
                enabled = setName.isNotBlank() && exerciseEntries.all { it.weight.isNotBlank() && it.reps.isNotBlank() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green, contentColor = White
                ),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text("Add Set")
            }

            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Green),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Green),
            ) {
                Text("Cancel")
            }
        }
    })
}

@Composable
fun ExerciseEntryRow(
    entry: ExerciseEntry,
    onWeightChange: (String) -> Unit,
    onRepsChange: (String) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, LightSage),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompactTextField(
                value = entry.weight,
                onValueChange = onWeightChange,
                label = "Weight",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            CompactTextField(
                value = entry.reps,
                onValueChange = onRepsChange,
                label = "Reps",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = DarkGreen)
            }
        }
    }
}