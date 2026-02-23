package com.example.workoutmate.ui.screen.dashboard.components.workoutform

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.Green
import com.example.workoutmate.ui.theme.LightGray

@Composable
fun Header(
    workoutTitle: String, onBackClick: () -> Unit, onSaveClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Green)
        }

        Text(
            text = "Add Workout",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGreen,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            enabled = workoutTitle.isNotEmpty(), onClick = onSaveClick
        ) {
            Icon(
                Icons.Filled.Save,
                contentDescription = "Save Workout",
                tint = if (workoutTitle.isNotEmpty()) Green else LightGray
            )
        }
    }
}