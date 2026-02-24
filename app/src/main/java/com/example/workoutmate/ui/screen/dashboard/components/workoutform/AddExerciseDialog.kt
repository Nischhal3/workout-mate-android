package com.example.workoutmate.ui.screen.dashboard.components.workoutform

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.workoutmate.ui.screen.components.AppButton
import com.example.workoutmate.ui.screen.components.InputTextField
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.Green
import com.example.workoutmate.ui.theme.LightGray
import com.example.workoutmate.ui.theme.LightSage

data class SetEntry(
    val weight: String, val reps: String
)

data class Exercise(
    val name: String, val exercises: List<SetEntry> = emptyList()
)

@Composable
fun AddExerciseDialog(
    onDismiss: () -> Unit, onAdd: (Exercise) -> Unit
) {
    val scrollState = rememberScrollState()

    var setName by remember { mutableStateOf("") }
    var setEntries by remember { mutableStateOf(listOf(SetEntry("", ""))) }

    AlertDialog(onDismissRequest = onDismiss, title = {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text("Add Exercise Set", fontWeight = FontWeight.Bold, color = DarkGreen)
            }
            IconButton(onClick = {
                setEntries = setEntries + SetEntry("", "")
            }) {
                Icon(
                    Icons.Default.Add,
                    tint = DarkGreen,
                    contentDescription = "Add Exercise",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }, text = {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-25).dp)
                .heightIn(max = 450.dp)
                .verticalScroll(scrollState)
        ) {
            InputTextField(
                value = setName,
                label = "Set Name",
                onValueChange = { setName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
            )

            setEntries.forEachIndexed { index, entry ->
                ExerciseEntryRow(
                    entry = entry,
                    deleteIconIsEnabled = setEntries.size > 1,
                    onWeightChange = { newWeight ->
                        setEntries = setEntries.toMutableList()
                            .also { it[index] = it[index].copy(weight = newWeight) }
                    },
                    onRepsChange = { newReps ->
                        setEntries = setEntries.toMutableList()
                            .also { it[index] = it[index].copy(reps = newReps) }
                    },
                    onDelete = {
                        setEntries = setEntries.toMutableList().also { it.removeAt(index) }
                    })
            }
        }
    }, confirmButton = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-15).dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppButton(
                text = "Save",
                enabled = setName.isNotBlank() && setEntries.all { it.weight.isNotBlank() && it.reps.isNotBlank() },
                onClick = {
                    if (setName.isNotBlank() && setEntries.isNotEmpty()) {
                        onAdd(Exercise(setName, setEntries))
                        onDismiss()
                    }
                },
                contentPadding = PaddingValues(
                    horizontal = 4.dp, vertical = 4.dp
                ),
                modifier = Modifier
                    .width(90.dp)
                    .height(45.dp),
            )

            AppButton(
                text = "Cancel",
                onClick = onDismiss,
                border = BorderStroke(1.dp, Green),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Green),
                modifier = Modifier
                    .width(90.dp)
                    .height(45.dp),
                contentPadding = PaddingValues(
                    horizontal = 4.dp, vertical = 4.dp
                )
            )
        }
    })
}

@Composable
fun ExerciseEntryRow(
    entry: SetEntry,
    onDelete: () -> Unit,
    deleteIconIsEnabled: Boolean,
    onWeightChange: (String) -> Unit,
    onRepsChange: (String) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, LightSage),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputTextField(
                label = "Weight",
                value = entry.weight,
                verticalPadding = 8.dp,
                onValueChange = onWeightChange,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            InputTextField(
                label = "Reps",
                value = entry.reps,
                verticalPadding = 8.dp,
                onValueChange = onRepsChange,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            IconButton(onClick = onDelete, enabled = deleteIconIsEnabled) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = if (deleteIconIsEnabled) DarkGreen else LightGray
                )
            }
        }
    }
}