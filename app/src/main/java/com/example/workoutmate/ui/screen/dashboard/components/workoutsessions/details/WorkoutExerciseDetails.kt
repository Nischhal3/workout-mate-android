package com.example.workoutmate.ui.screen.dashboard.components.workoutsessions.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workoutmate.data.WorkoutSessionWithExercisesAndSets
import com.example.workoutmate.ui.screen.components.Header
import com.example.workoutmate.ui.screen.components.VerticalScrollbar
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DividerColor
import com.example.workoutmate.ui.theme.Red
import com.example.workoutmate.ui.theme.White
import com.example.workoutmate.ui.viewmodel.UserViewModel

@Composable
fun WorkoutExerciseDetails(
    onBackClick: () -> Unit, userViewModel: UserViewModel, data: WorkoutSessionWithExercisesAndSets
) {
    val listState = rememberLazyListState()

    val exercises = remember(data.exercises) {
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
                onLeftClick = onBackClick,
                onRightClick = {},
                rightIcon = Icons.Filled.Save,
                leftIcon = Icons.AutoMirrored.Filled.ArrowBack
            )
            HorizontalDivider(
                color = DividerColor, thickness = 1.dp,
            )
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
                items(items = exercises, key = { it.exercise.id }) { exerciseWithSets ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Header(
                                onLeftClick = {},
                                onRightClick = {},
                                rightIconTint = Red,
                                leftIconTint = DarkGray,
                                leftIcon = Icons.Outlined.Edit,
                                title = exerciseWithSets.exercise.name,
                                rightIcon = Icons.Outlined.Delete,
                                modifier = Modifier.height(20.dp),
                                textStyle = MaterialTheme.typography.titleSmall,
                            )

                            HorizontalDivider(
                                color = DividerColor, thickness = 1.dp
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                exerciseWithSets.sets.sortedBy { it.setNumber }.forEach { set ->
                                    WorkoutSetRow(
                                        set = set, onCheckedChange = {})
                                }
                            }

                        }
                    }
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