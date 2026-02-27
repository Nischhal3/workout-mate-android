package com.example.workoutmate.ui.screen.dashboard.components.workoutform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutmate.model.SetEntry
import com.example.workoutmate.ui.screen.components.EditableSetRow
import com.example.workoutmate.ui.theme.Red

@Composable
fun DraftSetList(
    entries: List<SetEntry>,
    onDelete: (setIndex: Int) -> Unit,
    onUpdateSet: (setIndex: Int, entry: SetEntry) -> Unit,
) {
    var editingIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        entries.forEachIndexed { index, entry ->
            val reps = entry.reps
            val weight = entry.weight

            EditableSetRow(
                initialReps = reps.toInt(),
                displayTextFontSize = 14.sp,
                setLabel = "Set ${index + 1}",
                isEditing = editingIndex == index,
                initialWeightKg = weight.toDouble(),
                onEditClick = { editingIndex = index },
                onCancelEdit = { editingIndex = null },
                displayText = "$weight kg × $reps reps",
                onSave = { weight, reps ->
                    editingIndex = null
                    onUpdateSet(index, SetEntry(weight = weight.toString(), reps = reps.toString()))
                },
                trailingActions = {
                    IconButton(
                        onClick = { onDelete(index) }, modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            tint = Red,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                })
        }
    }
}