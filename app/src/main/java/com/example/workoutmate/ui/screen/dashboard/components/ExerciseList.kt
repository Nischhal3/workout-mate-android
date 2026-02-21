package com.example.workoutmate.ui.screen.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutmate.data.WorkoutDay
import com.example.workoutmate.ui.screen.components.VerticalScrollbar
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ExerciseList() {
    val listState = rememberLazyListState()
    val dummyWorkoutDays = listOf(

        WorkoutDay(
            workoutDayId = 1,
            userId = 1,
            title = "Chest Day",
            date = LocalDate.now(),
            notes = "Focused on incline press",
            startedAtEpochMs = 1708500000000,
            endedAtEpochMs = 1708503600000
        ),

        WorkoutDay(
            workoutDayId = 2,
            userId = 1,
            title = "Leg Day",
            date = LocalDate.now().minusDays(1),
            notes = "Heavy squats",
            startedAtEpochMs = 1708413600000,
            endedAtEpochMs = 1708417200000
        ),

        WorkoutDay(
            workoutDayId = 3,
            userId = 1,
            title = "Back Workout",
            date = LocalDate.now().minusDays(2),
            notes = "Deadlifts & rows",
            startedAtEpochMs = 1708327200000,
            endedAtEpochMs = 1708330800000
        ),

        WorkoutDay(
            workoutDayId = 4,
            userId = 1,
            title = "Shoulders",
            date = LocalDate.now().minusDays(3),
            notes = null,
            startedAtEpochMs = 1708240800000,
            endedAtEpochMs = 1708242600000
        ),

        WorkoutDay(
            workoutDayId = 5,
            userId = 1,
            title = "Arms",
            date = LocalDate.now().minusDays(4),
            notes = "Biceps + Triceps",
            startedAtEpochMs = 1708154400000,
            endedAtEpochMs = 1708158000000
        ), WorkoutDay(
            workoutDayId = 6,
            userId = 1,
            title = "Arms",
            date = LocalDate.now().minusDays(4),
            notes = "TEst + Triceps",
            startedAtEpochMs = 1708154400000,
            endedAtEpochMs = 1708158000000
        ), WorkoutDay(
            workoutDayId = 7,
            userId = 1,
            title = "Arms",
            date = LocalDate.now().minusDays(4),
            notes = "TEst + Triceps",
            startedAtEpochMs = 1708154400000,
            endedAtEpochMs = 1708158000000
        ), WorkoutDay(
            workoutDayId = 8,
            userId = 1,
            title = "Arms",
            date = LocalDate.now().minusDays(4),
            notes = "TEst + Triceps",
            startedAtEpochMs = 1708154400000,
            endedAtEpochMs = 1708158000000
        )
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 10.dp),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(dummyWorkoutDays) { day ->
                ExerciseCard(day)
            }
        }

        VerticalScrollbar(
            listState = listState, modifier = Modifier
                .fillMaxSize()
                .padding(end = 2.dp)
        )
    }
}

@Composable
fun ExerciseCard(day: WorkoutDay) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = day.title,
                        fontSize = 18.sp,
                        color = DarkGreen,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = day.date.format(
                            DateTimeFormatter.ofPattern(
                                "dd MMM yyyy", Locale.getDefault()
                            )
                        ), fontSize = 14.sp, color = DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    if (day.notes != null) {
                        Text(
                            text = day.notes,
                            fontSize = 12.sp,
                            color = DarkGreen,
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Text(
                        text = day.date.dayOfWeek.getDisplayName(
                            TextStyle.FULL, Locale.getDefault()
                        ), fontSize = 12.sp, color = DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = DarkGreen,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}