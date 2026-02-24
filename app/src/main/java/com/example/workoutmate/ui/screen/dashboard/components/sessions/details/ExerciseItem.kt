package com.example.workoutmate.ui.screen.dashboard.components.sessions.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workoutmate.data.WorkoutExerciseWithSets
import com.example.workoutmate.ui.screen.components.Header
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DividerColor
import com.example.workoutmate.ui.theme.Red
import com.example.workoutmate.ui.theme.White

@Composable
fun ExerciseItem(
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    exerciseWithSets: WorkoutExerciseWithSets,
    onSetCheckedChange: (setId: Long, checked: Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Header(
                rightIconTint = Red,
                onLeftClick = onEdit,
                leftIconTint = DarkGray,
                onRightClick = onDelete,
                leftIcon = Icons.Outlined.Edit,
                rightIcon = Icons.Outlined.Delete,
                title = exerciseWithSets.exercise.name,
                modifier = Modifier.height(20.dp),
                textStyle = MaterialTheme.typography.titleSmall,
            )

            HorizontalDivider(color = DividerColor, thickness = 1.dp)

            SetList(
                onCheckedChange = onSetCheckedChange,
                sets = exerciseWithSets.sets.sortedBy { it.setNumber },
            )
        }
    }
}
