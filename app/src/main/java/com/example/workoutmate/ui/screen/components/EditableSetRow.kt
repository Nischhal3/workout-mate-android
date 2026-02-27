package com.example.workoutmate.ui.screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.LightGray

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
    modifier: Modifier = Modifier,
) {
    if (isEditing) {
        SetEditor(
            initialReps = initialReps,
            initialWeightKg = initialWeightKg,
            cancelEdit = onCancelEdit,
            onSave = onSave
        )
    } else {
        Row(
            modifier = modifier
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
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = displayText,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = displayTextFontSize
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(32.dp)
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
}