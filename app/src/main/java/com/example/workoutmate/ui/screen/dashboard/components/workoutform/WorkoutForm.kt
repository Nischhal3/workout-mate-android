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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workoutmate.ui.screen.components.AnimatedErrorText
import com.example.workoutmate.ui.screen.components.DatePickerDialog
import com.example.workoutmate.ui.screen.components.Header
import com.example.workoutmate.ui.screen.components.InputTextField
import com.example.workoutmate.ui.screen.components.roundedTopBar
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.LightGreen
import com.example.workoutmate.ui.theme.LightSage
import com.example.workoutmate.ui.theme.White
import com.example.workoutmate.utils.toPrettyDateString
import com.example.workoutmate.viewmodel.UserViewModel
import com.example.workoutmate.viewmodel.WorkoutEditorViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate

@Composable
fun WorkoutForm(
    onBackClick: () -> Unit, userViewModel: UserViewModel
) {
    val workoutEditorViewModel: WorkoutEditorViewModel = viewModel()

    val newSetList by workoutEditorViewModel.newSetList.collectAsState()
    val workoutTitle by workoutEditorViewModel.workoutTitle.collectAsState()
    val exerciseName by workoutEditorViewModel.exerciseName.collectAsState()
    val draftExercises by workoutEditorViewModel.draftExercises.collectAsState()
    val addExerciseDialogIsVisible by workoutEditorViewModel.addExerciseDialogIsVisible.collectAsState()

    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var openDatePicker by remember { mutableStateOf(false) }
    var editingSetId by remember { mutableStateOf<String?>(null) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var editingExerciseId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(showError) {
        if (showError) {
            delay(2000)
            showError = false
            errorMessage = ""
        }
    }

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
                Box(modifier = Modifier.fillMaxSize()) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Header(
                            leftIconTint = White,
                            title = "Add Workout",
                            rightIconTint = White,
                            useCircularBackground = true,
                            onLeftIconClick = {
                                onBackClick()
                                workoutEditorViewModel.clearForm()
                            },
                            rightIcon = Icons.Filled.SaveAlt,
                            leftIcon = Icons.AutoMirrored.Filled.ArrowBack,
                            rightIconEnabled = workoutTitle.isNotEmpty() && editingSetId.isNullOrEmpty() && editingExerciseId.isNullOrEmpty(),
                            onRightIconClick = {
                                userViewModel.addWorkoutSession(
                                    date = selectedDate,
                                    title = workoutTitle,
                                    exercises = draftExercises,
                                    onError = { msg ->
                                        errorMessage = msg
                                        showError = true
                                    },
                                    onSuccess = {
                                        onBackClick()
                                        workoutEditorViewModel.clearForm()
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
                                onValueChange = { workoutEditorViewModel.setWorkoutTitle(it) },
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

                        DraftExerciseList(
                            editingSetId = editingSetId,
                            exercises = draftExercises,
                            enabled = workoutTitle.isNotEmpty(),
                            editingExerciseId = editingExerciseId,
                            setEditingSetId = { editingSetId = it },
                            setEditingExerciseId = { editingExerciseId = it },
                            onDeleteSet = workoutEditorViewModel::deleteDraftSet,
                            onUpdateSet = workoutEditorViewModel::updateDraftSet,
                            onAddSet = workoutEditorViewModel::openAddExerciseDialog,
                            onDeleteExercise = workoutEditorViewModel::deleteDraftExercise,
                            updateExerciseName = workoutEditorViewModel::updateDraftExerciseName
                        )

                        if (addExerciseDialogIsVisible) {
                            AddExerciseDialog(
                                setList = newSetList,
                                exerciseName = exerciseName,
                                addNewSet = workoutEditorViewModel::addNewSet,
                                deleteNewSet = workoutEditorViewModel::deleteNewSet,
                                addNewExercise = workoutEditorViewModel::addNewExercise,
                                onDismiss = workoutEditorViewModel::closeAddExerciseDialog,
                                onSetFieldChange = workoutEditorViewModel::onSetFieldChange,
                                onExerciseNameChange = workoutEditorViewModel::onExerciseNameChange
                            )
                        }
                    }

                    AnimatedErrorText(
                        visible = showError,
                        message = errorMessage,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp),
                        textModifier = Modifier
                            .background(Color.Transparent)
                            .padding(8.dp)
                    )
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