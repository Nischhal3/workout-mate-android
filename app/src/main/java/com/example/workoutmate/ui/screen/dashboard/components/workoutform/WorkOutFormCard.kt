package com.example.workoutmate.ui.screen.dashboard.components.workoutform

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutmate.ui.screen.components.InputTextField
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen

@Composable
fun WorkoutFormCard(
    selectedDate: String,
    onDateClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var workoutTitle by remember { mutableStateOf("") }
    var showSetForm by remember { mutableStateOf(false) }
    var exercises by remember { mutableStateOf(listOf<ExerciseSet>()) }

    Box(
        modifier = modifier.background(Color.White, shape = RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            WorkoutCardHeader(
                workoutTitle = workoutTitle,
                onBackClick = onBackClick,
                onSaveClick = { /* handle save */ })

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Selected date: $selectedDate",
                    color = DarkGray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )

                InputTextField(
                    value = workoutTitle,
                    label = "Workout Title",
                    onValueChange = { workoutTitle = it },
                    trailingContent = {
                        IconButton(onClick = onDateClick) {
                            Icon(
                                Icons.Default.DateRange,
                                contentDescription = "Select Date",
                                tint = DarkGreen
                            )
                        }
                    })
            }

            AddedSetsList(
                exercises = exercises,
                onAddSet = { showSetForm = true },
                enabled = workoutTitle.isNotEmpty()
            ) { set ->
                exercises = exercises - set
            }

            if (showSetForm) {
                AddExerciseDialog(onDismiss = { showSetForm = false }, onAdd = { newSet ->
                    exercises = exercises + newSet
                    showSetForm = false
                })
            }
        }
    }
}