package com.example.workoutmate.ui.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workoutmate.ui.screen.components.DatePickerDialog
import com.example.workoutmate.ui.screen.dashboard.components.DashboardHeader
import com.example.workoutmate.ui.screen.dashboard.components.DashboardNavBar
import com.example.workoutmate.ui.screen.dashboard.components.ExerciseList
import com.example.workoutmate.ui.theme.LightSage
import com.example.workoutmate.ui.theme.LightGreen
import com.example.workoutmate.ui.theme.White
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
            .background(White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            DashboardHeader(username = "Nischhal", onProfileClick = { }, onSettingsClick = { })
            HorizontalDivider(Modifier.height(1.dp), color = LightSage)

            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(LightGreen)
            ) {
                ExerciseList()
            }
            Box(modifier = Modifier.background(LightGreen)) {
                DashboardNavBar(onHomeClick = { }, onAddClick = { openDialog = true })
            }
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