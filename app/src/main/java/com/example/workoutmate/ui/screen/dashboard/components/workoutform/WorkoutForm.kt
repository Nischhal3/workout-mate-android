package com.example.workoutmate.ui.screen.dashboard.components.workoutform

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.workoutmate.ui.screen.components.DatePickerDialog
import com.example.workoutmate.ui.screen.components.InputTextField
import com.example.workoutmate.ui.screen.components.roundedTopBar
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.LightGreen
import com.example.workoutmate.ui.theme.LightSage
import com.example.workoutmate.ui.viewmodel.UserViewModel
import com.example.workoutmate.utils.toPrettyDateString
import java.time.LocalDate

@Composable
fun WorkoutForm(
    onBackClick: () -> Unit, userViewModel: UserViewModel
) {
    var workoutTitle by remember { mutableStateOf("") }
    var showSetForm by remember { mutableStateOf(false) }
    var openDatePicker by remember { mutableStateOf(false) }
    var exercises by remember { mutableStateOf(listOf<Exercise>()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .roundedTopBar(
                strokeWidth = 1.dp, leftColor = LightSage, rightColor = LightSage, radius = 20.dp
            ), color = DarkGreen, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(DarkGreen)
                )
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                        .background(LightGreen)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f)
                    .offset(y = (-30).dp)
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight(0.80f)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    Header(
                        workoutTitle = workoutTitle, onBackClick = onBackClick, onSaveClick = {
                            userViewModel.addWorkoutSession(
                                title = workoutTitle,
                                date = selectedDate,
                                exercises = exercises,
                                onError = { message ->
                                },
                                onSuccess = {
                                    onBackClick()
                                })
                        })

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Selected date: ${selectedDate.toPrettyDateString()}",
                            color = DarkGray,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        InputTextField(
                            value = workoutTitle,
                            label = "Workout Title",
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = { workoutTitle = it },
                            trailingContent = {
                                IconButton(onClick = { openDatePicker = true }) {
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
            DatePickerDialog(
                open = openDatePicker,
                disablePastDates = true,
                onDismiss = { openDatePicker = false },
                onDateSelected = { date -> selectedDate = date })
        }
    }
}