package com.example.workoutmate.ui.screen.dashboard.components.sessions.details

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutmate.data.WorkoutSet
import com.example.workoutmate.ui.screen.components.SetEditor
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.LightGray

@Composable
fun SetList(
    sets: List<WorkoutSet>,
    onCheckedChange: (setId: Long, checked: Boolean) -> Unit,
    onSaveSet: (setId: Long, weight: Double, reps: Int) -> Unit
) {
    var editingSetId by remember { mutableStateOf<Long?>(null) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        sets.forEach { set ->
            if (editingSetId == set.id) {
                SetEditor(
                    initialReps = set.reps,
                    initialWeightKg = set.weightKg,
                    cancelEdit = { editingSetId = null },
                    onSave = { weight, reps ->
                        editingSetId = null
                        onSaveSet(set.id, weight, reps)
                    })

            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, LightGray, RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Set ${set.setNumber}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = Modifier.weight(2f), contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "${set.weightKg} kg × ${set.reps} reps",
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 16.sp
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        IconButton(
                            onClick = { editingSetId = set.id }, // 👈 track clicked set
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Edit set",
                                tint = DarkGray,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Box(
                            modifier = Modifier.size(32.dp), contentAlignment = Alignment.Center
                        ) {
                            Checkbox(
                                checked = set.completed,
                                onCheckedChange = { checked ->
                                    onCheckedChange(set.id, checked)
                                },
                                modifier = Modifier.size(18.dp),
                                colors = CheckboxDefaults.colors(
                                    checkedColor = DarkGreen,
                                    checkmarkColor = Color.White,
                                    uncheckedColor = DarkGray.copy(alpha = 0.50f)
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}