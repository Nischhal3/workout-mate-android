package com.example.workoutmate.ui.screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.DividerColor
import com.example.workoutmate.ui.theme.Green
import com.example.workoutmate.ui.theme.White
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    open: Boolean,
    onDismiss: () -> Unit,
    disablePastDates: Boolean = true,
    onDateSelected: (LocalDate) -> Unit,
) {
    if (!open) return

    val today = remember { LocalDate.now() }

    fun localDateToUtcStartOfDayMillis(date: LocalDate): Long =
        date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

    fun utcMillisToLocalDate(utcMillis: Long): LocalDate =
        Instant.ofEpochMilli(utcMillis).atZone(ZoneOffset.UTC).toLocalDate()

    val datePickerState =
        rememberDatePickerState(
            initialSelectedDateMillis = localDateToUtcStartOfDayMillis(LocalDate.now()),
            initialDisplayMode = DisplayMode.Picker,
            selectableDates = if (disablePastDates) {
                object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                        val picked = utcMillisToLocalDate(utcTimeMillis)
                        return !picked.isBefore(today)
                    }

                    override fun isSelectableYear(year: Int): Boolean = year >= today.year
                }
            } else object : SelectableDates {})

    Dialog(
        onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    color = DarkGreen,
                    text = "Select date",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider(color = DividerColor, thickness = 1.dp)

                Spacer(modifier = Modifier.height(6.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    DatePicker(
                        title = null,
                        headline = null,
                        showModeToggle = false,
                        state = datePickerState,
                        modifier = Modifier.fillMaxWidth(),
                        colors = DatePickerDefaults.colors(
                            containerColor = White,
                            dayContentColor = DarkGreen,
                            weekdayContentColor = DarkGreen,
                            selectedDayContentColor = White,
                            selectedDayContainerColor = Green,
                            navigationContentColor = DarkGreen,
                        )
                    )

                    HorizontalDivider(
                        color = DividerColor,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = 112.dp)
                            .padding(horizontal = 16.dp)
                    )
                }

                HorizontalDivider(
                    color = DividerColor, thickness = 1.dp, modifier = Modifier.offset(y = (-26).dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .offset(y = (-14).dp), horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Green),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Green),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text("CANCEL")
                    }

                    Button(
                        onClick = {
                            val ms = datePickerState.selectedDateMillis
                            if (ms != null) onDateSelected(utcMillisToLocalDate(ms))
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green, contentColor = White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}