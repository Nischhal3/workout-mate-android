package com.example.workoutmate.ui.screen.dashboard.components.sessions.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.workoutmate.data.WorkoutSet
import com.example.workoutmate.ui.screen.components.EditableSetRow
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen

@Composable
fun SetList(
    sets: List<WorkoutSet>,
    onCheckedChange: (setId: Long, checked: Boolean) -> Unit,
    onSaveSet: (setId: Long, weight: Double, reps: Int) -> Unit
) {
    var editingSetId by remember { mutableStateOf<Long?>(null) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        sets.forEach { set ->
            EditableSetRow(
                initialReps = set.reps,
                initialWeightKg = set.weightKg,
                setLabel = "Set ${set.setNumber}",
                isEditing = editingSetId == set.id,
                onCancelEdit = { editingSetId = null },
                onEditClick = { editingSetId = set.id },
                displayText = "${set.weightKg} kg × ${set.reps} reps",
                onSave = { weight, reps ->
                    editingSetId = null
                    onSaveSet(set.id, weight, reps)
                },
                trailingActions = {
                    Box(
                        modifier = Modifier.size(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Checkbox(
                            checked = set.completed,
                            onCheckedChange = { checked -> onCheckedChange(set.id, checked) },
                            modifier = Modifier.size(18.dp),
                            colors = CheckboxDefaults.colors(
                                checkedColor = DarkGreen,
                                checkmarkColor = Color.White,
                                uncheckedColor = DarkGray.copy(alpha = 0.50f)
                            )
                        )
                    }
                }
            )
        }
    }
}