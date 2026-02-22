package com.example.workoutmate.ui.screen.dashboard.components.workoutform

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.workoutmate.ui.screen.components.DatePickerDialog
import com.example.workoutmate.ui.screen.components.roundedTopBar
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.LightGreen
import com.example.workoutmate.ui.theme.LightSage
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun WorkoutForm(
    onBackClick: () -> Unit
) {
    var openDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now().toPrettyDateString()) }

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

            WorkoutFormCard(
                selectedDate = selectedDate,
                onDateClick = { openDatePicker = true },
                onBackClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f)
                    .offset(y = (-30).dp)
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight(0.80f)
            )

            DatePickerDialog(
                open = openDatePicker,
                disablePastDates = true,
                onDismiss = { openDatePicker = false },
                onDateSelected = { date -> selectedDate = date.toPrettyDateString() })
        }
    }
}

fun LocalDate.toPrettyDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    return this.format(formatter)
}