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
    setList: List<SetEntry>,
    onDelete: (setId: String) -> Unit,
    onUpdateSet: (entry: SetEntry) -> Unit,
) {
    var editingId by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        setList.forEachIndexed { index, set ->
            val id = set.id
            val reps = set.reps
            val weight = set.weight

            EditableSetRow(
                initialReps = reps.toInt(),
                displayTextFontSize = 14.sp,
                setLabel = "Set ${index + 1}",
                isEditing = editingId == id,
                initialWeightKg = weight.toDouble(),
                onEditClick = { editingId = id },
                onCancelEdit = { editingId = null },
                displayText = "$weight kg × $reps reps",
                onSave = { weight, reps ->
                    editingId = null
                    onUpdateSet(
                        SetEntry(id = id, weight = weight.toString(), reps = reps.toString())
                    )
                },
                trailingActions = {
                    IconButton(
                        onClick = { onDelete(id) }, modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Delete,
                            tint = Red,
                            contentDescription = "Delete",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                })
        }
    }
}