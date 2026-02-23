package com.example.workoutmate.ui.screen.dashboard.components.workoutform

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.Green
import com.example.workoutmate.ui.theme.LightGray
import com.example.workoutmate.ui.theme.White

@Composable
fun AddedSetsList(
    enabled: Boolean,
    onAddSet: () -> Unit,
    exercises: List<ExerciseSet>,
    onDeleteSet: (ExerciseSet) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Added Exercise Sets",
                fontWeight = FontWeight.Bold,
                color = DarkGreen,
                fontSize = 16.sp
            )

            IconButton(onClick = onAddSet, enabled = enabled) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Set",
                    modifier = Modifier.size(28.dp),
                    tint = if (enabled) Green else LightGray
                )
            }
        }

        HorizontalDivider(
            color = LightGray,
            thickness = 1.dp,
        )
        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()
        ) {
            items(exercises, key = { it.hashCode() }) { exerciseSet ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    border = BorderStroke(1.dp, LightGray)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 12.dp, end = 12.dp, top = 2.dp, bottom = 4.dp
                            ), verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.weight(1f), contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = exerciseSet.name,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkGreen,
                                    textAlign = TextAlign.Center,
                                )
                            }

                            IconButton(
                                onClick = { onDeleteSet(exerciseSet) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete Set",
                                    tint = DarkGreen,
                                )
                            }
                        }

                        exerciseSet.exercises.forEach { entry ->
                            Text(
                                text = "${entry.weight} kg × ${entry.reps} reps",
                                color = DarkGray,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
