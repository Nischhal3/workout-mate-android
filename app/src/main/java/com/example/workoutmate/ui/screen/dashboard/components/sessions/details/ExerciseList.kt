package com.example.workoutmate.ui.screen.dashboard.components.sessions.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workoutmate.data.WorkoutExerciseWithSets

@Composable
fun ExerciseList(
    listState: LazyListState,
    exercises: List<WorkoutExerciseWithSets>,
    onEditExercise: (exerciseId: Long) -> Unit,
    onDeleteExercise: (exerciseId: Long) -> Unit,
    onSetCheckedChange: (setId: Long, checked: Boolean) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 10.dp),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = exercises, key = { it.exercise.id }) { exerciseWithSets ->
            ExerciseItem(
                exerciseWithSets = exerciseWithSets,
                onSetCheckedChange = onSetCheckedChange,
                onEdit = { onEditExercise(exerciseWithSets.exercise.id) },
                onDelete = { onDeleteExercise(exerciseWithSets.exercise.id) })
        }
    }
}