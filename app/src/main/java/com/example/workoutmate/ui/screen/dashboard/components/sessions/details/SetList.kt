package com.example.workoutmate.ui.screen.dashboard.components.sessions.details

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.workoutmate.data.WorkoutSet
import com.example.workoutmate.ui.theme.LightGray

@Composable
fun SetList(
    sets: List<WorkoutSet>, onCheckedChange: (setId: Long, checked: Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        sets.forEach { set ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, LightGray, RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Set ${set.setNumber}",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = if (set.completed) "Completed" else "In progress",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                Text(
                    text = "${set.weightKg} kg",
                    modifier = Modifier.padding(end = 8.dp),
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = "${set.reps} reps",
                    modifier = Modifier.padding(end = 10.dp),
                    style = MaterialTheme.typography.bodySmall,
                )

                Checkbox(
                    checked = set.completed, onCheckedChange = {})
            }
        }
    }
}