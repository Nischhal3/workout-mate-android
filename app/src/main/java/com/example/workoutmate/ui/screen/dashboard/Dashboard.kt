package com.example.workoutmate.ui.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workoutmate.ui.screen.components.DatePickerDialog
import com.example.workoutmate.ui.screen.dashboard.components.DashboardNavBar
import com.example.workoutmate.ui.theme.LightGreen
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard() {
    var openDialog by remember { mutableStateOf(false) }
    var selectedDateLabel by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(LightGreen)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(20.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedDateLabel.ifBlank { "No date selected" },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            DashboardNavBar(onHomeClick = { }, onAddClick = { openDialog = true })
        }
    }

    DatePickerDialog(
        open = openDialog,
        onDismiss = { openDialog = false },
        disablePastDates = true,
        onDateSelected = { date ->
            selectedDateLabel = date.toPrettyDateString()
        })
}

fun LocalDate.toPrettyDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    return this.format(formatter)
}