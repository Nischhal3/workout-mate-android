package com.example.workoutmate.ui.screen.dashboard.components.workoutform

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutmate.model.Exercise
import com.example.workoutmate.model.SetEntry
import com.example.workoutmate.ui.screen.components.CustomIcon
import com.example.workoutmate.ui.screen.components.ExerciseNameEditor
import com.example.workoutmate.ui.screen.components.Header
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.DividerColor
import com.example.workoutmate.ui.theme.LightGray
import com.example.workoutmate.ui.theme.Red
import com.example.workoutmate.ui.theme.White

@Composable
fun AddedSetsList(
    enabled: Boolean,
    onAddSet: () -> Unit,
    exercises: List<Exercise>,
    onDeleteSet: (Exercise) -> Unit,
    updateExerciseName: (index: Int, newName: String) -> Unit
) {
    var editMode by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Added Exercise Sets",
                fontWeight = FontWeight.Bold,
                color = DarkGreen,
                fontSize = 16.sp
            )

            CustomIcon(
                iconTint = White,
                enabled = enabled,
                onClick = onAddSet,
                icon = Icons.Default.Add,
                useCircularBackground = true,
                contentDescription = "Add Set Action"
            )
        }

        HorizontalDivider(
            color = LightGray,
            thickness = 1.dp,
        )
        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(exercises) { exerciseIndex, exerciseSet ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    border = BorderStroke(1.dp, LightGray)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (!editMode) {
                            Header(
                                rightIconTint = Red,
                                onLeftIconClick = {
                                    editMode = true
                                },
                                leftIconTint = DarkGray,
                                title = exerciseSet.name,
                                leftIcon = Icons.Outlined.Edit,
                                rightIcon = Icons.Outlined.Delete,
                                modifier = Modifier.height(20.dp),
                                onRightIconClick = { onDeleteSet(exerciseSet) },
                                textStyle = MaterialTheme.typography.titleSmall,
                            )
                        } else {
                            ExerciseNameEditor(
                                exerciseName = exerciseSet.name,
                                cancelEdit = { editMode = false },
                                onSave = { newName ->
                                    editMode = false
                                    updateExerciseName(exerciseIndex, newName)
                                })
                        }

                        HorizontalDivider(
                            color = DividerColor,
                            thickness = 1.dp,
                        )

                        ExerciseEntryList(
                            entries = exerciseSet.exercises,
                            onEdit = { index, entry -> /* open edit dialog */ },
                            onDelete = { index, entry -> /* remove entry */ })
                    }
                }
            }
        }
    }
}


@Composable
fun ExerciseEntryList(
    entries: List<SetEntry>,
    onEdit: (index: Int, entry: SetEntry) -> Unit,
    onDelete: (index: Int, entry: SetEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        entries.forEachIndexed { index, entry ->
            ExerciseEntryRow(
                index = index,
                entry = entry,
                onDelete = { onDelete(index, entry) },
                onEditCommit = { newW, newR -> /* update entry in VM/state */ },
            )
        }
    }
}


@Composable
fun ExerciseEntryRow(
    index: Int,
    entry: SetEntry,
    onEditCommit: (newWeight: Float, newReps: Int) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(12.dp)

    var isEditing by remember(entry) { mutableStateOf(false) }

    var weightText by remember(entry) { mutableStateOf(entry.weight.toString()) }
    var repsText by remember(entry) { mutableStateOf(entry.reps.toString()) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .border(1.dp, LightGray, shape)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (!isEditing) {
            Text(
                text = "${entry.weight} kg × ${entry.reps} reps",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f),
                color = DarkGray
            )
        } else {
            // Inline edit UI (two small inputs)
            Row(
                modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = weightText,
                    onValueChange = { weightText = it },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    label = { Text("kg") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(Modifier.width(8.dp))
                OutlinedTextField(
                    value = repsText,
                    onValueChange = { repsText = it },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    label = { Text("reps") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }

        if (!isEditing) {
            IconButton(onClick = { isEditing = true }, modifier = Modifier.size(32.dp)) {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = "Edit",
                    tint = DarkGray,
                    modifier = Modifier.size(18.dp)
                )
            }
            IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                Icon(
                    Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = Red,
                    modifier = Modifier.size(18.dp)
                )
            }
        } else {
            IconButton(
                onClick = {
                    val w = weightText.replace(',', '.').toFloatOrNull()
                    val r = repsText.toIntOrNull()
                    if (w != null && r != null) {
                        onEditCommit(w, r)
                        isEditing = false
                    }
                }, modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Outlined.Check,
                    contentDescription = "Save",
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(18.dp)
                )
            }

            IconButton(
                onClick = {
                    weightText = entry.weight
                    repsText = entry.reps
                    isEditing = false
                }, modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Outlined.Close,
                    contentDescription = "Cancel",
                    tint = DarkGray,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}