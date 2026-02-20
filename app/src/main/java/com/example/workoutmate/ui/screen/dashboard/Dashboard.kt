package com.example.workoutmate.ui.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.workoutmate.ui.screen.components.DatePickerDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Dashboard() {
    var openDialog by remember { mutableStateOf(false) }
    var selectedDateLabel by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { openDialog = true }) {
            Text("Open Date Picker")
        }

        Text("Selected Date: $selectedDateLabel")
    }

    DatePickerDialog(
        open = openDialog,
        onDismiss = { openDialog = false },
        disablePastDates = true,
        onDateSelected = { date ->
            selectedDateLabel = date.toPrettyDateString()
        }
    )
}

fun LocalDate.toPrettyDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    return this.format(formatter)
}