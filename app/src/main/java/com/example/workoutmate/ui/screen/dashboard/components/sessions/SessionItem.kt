package com.example.workoutmate.ui.screen.dashboard.components.sessions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.workoutmate.data.WorkoutSession
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.viewmodel.UserViewModel
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun SessionItem(
    session: WorkoutSession,
    userViewModel: UserViewModel,
    onCopyClick: (WorkoutSession) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                userViewModel.observeSessionWithExercisesAndSets(workoutSessionId = session.id)
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 40.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = session.title, style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = session.date.dayOfWeek.getDisplayName(
                        TextStyle.FULL, Locale.getDefault()
                    ), style = MaterialTheme.typography.bodyMedium
                )
            }

            Column(
                modifier = Modifier.align(Alignment.TopEnd),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {

                IconButton(
                    onClick = { onCopyClick(session) }, modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        contentDescription = "Copy",
                        tint = DarkGreen
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = DarkGreen,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}