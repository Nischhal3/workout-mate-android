package com.example.workoutmate.ui.screen.dashboard.components.sessions.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workoutmate.data.WorkoutSessionWithExercisesAndSets
import com.example.workoutmate.ui.screen.components.Header
import com.example.workoutmate.ui.screen.components.VerticalScrollbar
import com.example.workoutmate.ui.theme.DividerColor
import com.example.workoutmate.viewmodel.UserViewModel

@Composable
fun Session(
    onBackClick: () -> Unit,
    userViewModel: UserViewModel,
    onSaveClick: () -> Unit = {},
    data: WorkoutSessionWithExercisesAndSets,
) {
    val listState = rememberLazyListState()

    val exercises = remember(data) {
        data.exercises.sortedBy { it.exercise.orderIndex }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Header(
                title = data.session.title,
                onLeftIconClick = onBackClick,
                onRightIconClick = onSaveClick,
                rightIcon = Icons.Filled.Save,
                leftIcon = Icons.AutoMirrored.Filled.ArrowBack
            )
            HorizontalDivider(color = DividerColor, thickness = 1.dp)
        }

        Box(modifier = Modifier.weight(1f)) {
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

                    Exercise(
                        exerciseWithSets = exerciseWithSets,
                        onSetCheckedChange = { setId, checked ->
                            // your TODO
                        },
                        onDelete = {
                            //userViewModel.deleteExercise(exerciseWithSets.exercise.id)
                        },
                        updateExerciseName = { newName ->
                            userViewModel.updateExerciseName(
                                exerciseWithSets.exercise.id, newName
                            )
                        })
                }
            }

            VerticalScrollbar(
                listState = listState,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd)
                    .padding(end = 2.dp)
            )
        }
    }
}