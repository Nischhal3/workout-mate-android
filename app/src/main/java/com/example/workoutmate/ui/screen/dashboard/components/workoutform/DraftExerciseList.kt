package com.example.workoutmate.ui.screen.dashboard.components.workoutform

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutmate.model.Exercise
import com.example.workoutmate.model.SetEntry
import com.example.workoutmate.ui.screen.components.CustomIcon
import com.example.workoutmate.ui.screen.components.EditableSetRow
import com.example.workoutmate.ui.screen.components.ExerciseNameEditor
import com.example.workoutmate.ui.screen.components.Header
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.DividerColor
import com.example.workoutmate.ui.theme.LightGray
import com.example.workoutmate.ui.theme.Red
import com.example.workoutmate.ui.theme.White

@Composable
fun DraftExerciseList(
    enabled: Boolean,
    onAddSet: () -> Unit,
    editingSetId: String?,
    exercises: List<Exercise>,
    editingExerciseId: String?,
    setEditingSetId: (value: String?) -> Unit,
    onDeleteExercise: (exerciseId: String) -> Unit,
    setEditingExerciseId: (value: String?) -> Unit,
    onDeleteSet: (setId: String, id: String) -> Unit,
    onUpdateSet: (exerciseId: String, entry: SetEntry) -> Unit,
    updateExerciseName: (exerciseId: String, newName: String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Draft Exercises & Sets",
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
            items(items = exercises, key = { it.id }) { exercise ->
                val name = exercise.name
                val exerciseId = exercise.id

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
                        if (editingExerciseId == exerciseId) {
                            ExerciseNameEditor(
                                exerciseName = name,
                                cancelEdit = { setEditingExerciseId(null) },
                                onSave = { newName ->
                                    setEditingExerciseId(null)
                                    updateExerciseName(exerciseId, newName)
                                })
                        } else {
                            Header(
                                rightIconTint = Red,
                                onLeftIconClick = { setEditingExerciseId(exerciseId) },
                                leftIconTint = DarkGray,
                                title = name,
                                leftIcon = Icons.Outlined.Edit,
                                rightIcon = Icons.Outlined.Delete,
                                modifier = Modifier.height(20.dp),
                                textStyle = MaterialTheme.typography.titleSmall,
                                onRightIconClick = { onDeleteExercise(exerciseId) },
                            )
                        }

                        HorizontalDivider(color = DividerColor, thickness = 1.dp)

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            exercise.setList.forEachIndexed { index, set ->
                                val setId = set.id
                                val reps = set.reps
                                val weight = set.weight

                                EditableSetRow(
                                    initialReps = reps.toInt(),
                                    displayTextFontSize = 14.sp,
                                    setLabel = "Set ${index + 1}",
                                    isEditing = editingSetId == setId,
                                    initialWeightKg = weight.toDouble(),
                                    displayText = "$weight kg × $reps reps",
                                    onEditClick = { setEditingSetId(setId) },
                                    onCancelEdit = { setEditingSetId(null) },
                                    onSave = { weight, reps ->
                                        setEditingSetId(null)
                                        onUpdateSet(
                                            exerciseId, SetEntry(
                                                id = setId,
                                                reps = reps.toString(),
                                                weight = weight.toString()
                                            )
                                        )
                                    },
                                    trailingActions = {
                                        IconButton(
                                            modifier = Modifier.size(32.dp),
                                            onClick = { onDeleteSet(setId, exerciseId) }) {
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
                }
            }
        }
    }
}

