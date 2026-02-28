package com.example.workoutmate.ui.screen.dashboard.components.workoutform

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.workoutmate.model.SetEntry
import com.example.workoutmate.ui.screen.components.AnimatedErrorText
import com.example.workoutmate.ui.screen.components.AppButton
import com.example.workoutmate.ui.screen.components.CustomIcon
import com.example.workoutmate.ui.screen.components.InputTextField
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.Green
import com.example.workoutmate.ui.theme.LightGray
import com.example.workoutmate.ui.theme.LightSage
import com.example.workoutmate.ui.theme.Red
import com.example.workoutmate.ui.theme.White

@Composable
fun AddExerciseDialog(
    exerciseName: String,
    onDismiss: () -> Unit,
    addNewSet: () -> Unit,
    setList: List<SetEntry>,
    addNewExercise: () -> Unit,
    deleteNewSet: (id: String) -> Unit,
    onExerciseNameChange: (exerciseName: String) -> Unit,
    onSetFieldChange: (
        setId: String, reps: String?, weight: String?
    ) -> Unit
) {
    val isFormValid = exerciseName.isNotBlank() && setList.isNotEmpty() && setList.all { entry ->
        entry.weight.toDoubleOrNull() != null && entry.reps.toIntOrNull() != null
    }

    AlertDialog(onDismissRequest = onDismiss, title = {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text("New Exercise", fontWeight = FontWeight.Bold, color = DarkGreen)
            }

            CustomIcon(
                iconTint = White,
                icon = Icons.Filled.SaveAlt,
                useCircularBackground = true,
                contentDescription = "Save Action",
                enabled = isFormValid,
                onClick = {
                    if (isFormValid) {
                        addNewExercise()
                        onDismiss()
                    }
                })
        }
    }, text = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-25).dp)
        ) {

            InputTextField(
                value = exerciseName,
                label = "Exercise Name",
                onValueChange = onExerciseNameChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 12.dp),
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 450.dp),
            ) {

                items(
                    items = setList, key = { it.id }) { entry ->

                    NewSetEntryRow(
                        reps = entry.reps,
                        weight = entry.weight,
                        deleteIconIsEnabled = setList.size > 1,
                        onDelete = {
                            deleteNewSet(entry.id)
                        },
                        onWeightChange = { onSetFieldChange(entry.id, null, it) },
                        onRepsChange = { onSetFieldChange(entry.id, it, null) })
                }
            }
        }
    }, confirmButton = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-15).dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppButton(
                text = "Add Set",
                onClick = addNewSet,
                contentPadding = PaddingValues(
                    horizontal = 4.dp, vertical = 4.dp
                ),
                modifier = Modifier
                    .width(90.dp)
                    .height(45.dp),
            )

            AppButton(
                text = "Cancel",
                onClick = onDismiss,
                border = BorderStroke(1.dp, Green),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Green),
                modifier = Modifier
                    .width(90.dp)
                    .height(45.dp),
                contentPadding = PaddingValues(
                    horizontal = 4.dp, vertical = 4.dp
                )
            )
        }
    })
}

@Composable
private fun NewSetEntryRow(
    reps: String,
    weight: String,
    onDelete: () -> Unit,
    deleteIconIsEnabled: Boolean,
    onRepsChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
) {
    val isRepsError = reps.isNotBlank() && reps.toIntOrNull() == null
    val isWeightError = weight.isNotBlank() && weight.toDoubleOrNull() == null

    val hasError = isWeightError || isRepsError

    val errorMessage = buildString {
        if (isWeightError) append("Weight must be a number. ")
        if (isRepsError) append("Reps must be a whole number.")
    }.trim()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, LightSage),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InputTextField(
                    value = weight,
                    label = "Weight",
                    verticalPadding = 8.dp,
                    onValueChange = { onWeightChange(it) },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                InputTextField(
                    value = reps,
                    label = "Reps",
                    verticalPadding = 8.dp,
                    onValueChange = { onRepsChange(it) },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                IconButton(onClick = onDelete, enabled = deleteIconIsEnabled) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = if (deleteIconIsEnabled) Red else LightGray
                    )
                }
            }
        }

        AnimatedErrorText(
            visible = hasError,
            message = errorMessage,
            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
        )
    }
}