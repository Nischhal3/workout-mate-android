package com.example.workoutmate.ui.screen.dashboard.components.workoutform

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
fun DraftExerciseList(
    enabled: Boolean,
    onAddSet: () -> Unit,
    exercises: List<Exercise>,
    onDeleteExercise: (id: String) -> Unit,
    onDeleteSet: (setId: String, id: String) -> Unit,
    updateExerciseName: (id: String, newName: String) -> Unit,
    onUpdateSet: (id: String, entry: SetEntry) -> Unit
) {
    var editingId by remember { mutableStateOf<String?>(null) }

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
            items(items = exercises, key = { it.id }) { exercise ->
                val id = exercise.id
                val name = exercise.name

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
                        if (editingId == id) {
                            ExerciseNameEditor(
                                exerciseName = name,
                                cancelEdit = { editingId = null },
                                onSave = { newName ->
                                    editingId = null
                                    updateExerciseName(id, newName)
                                })
                        } else {
                            Header(
                                rightIconTint = Red,
                                onLeftIconClick = {
                                    editingId = id
                                },
                                leftIconTint = DarkGray,
                                title = name,
                                leftIcon = Icons.Outlined.Edit,
                                rightIcon = Icons.Outlined.Delete,
                                modifier = Modifier.height(20.dp),
                                textStyle = MaterialTheme.typography.titleSmall,
                                onRightIconClick = { onDeleteExercise(id) },
                            )
                        }

                        HorizontalDivider(color = DividerColor, thickness = 1.dp)

                        DraftSetList(
                            setList = exercise.setList,
                            onDelete = { setId -> onDeleteSet(setId, id) },
                            onUpdateSet = { entry ->
                                onUpdateSet(
                                    id, entry
                                )
                            })
                    }
                }
            }
        }
    }
}

