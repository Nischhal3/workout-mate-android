package com.example.workoutmate.ui.screen.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutmate.ui.screen.components.roundedTopBar
import com.example.workoutmate.ui.theme.DarkGreen
import com.example.workoutmate.ui.theme.LightGreen
import com.example.workoutmate.ui.theme.LightSage

@Composable
fun DashboardNavBar(
    onHomeClick: () -> Unit,
    onAddClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .roundedTopBar(
                strokeWidth = 1.dp, leftColor = LightSage, rightColor = LightSage, radius = 20.dp
            ), color = DarkGreen, shape = RoundedCornerShape(
            topStart = 20.dp, topEnd = 20.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.width(48.dp), contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onHomeClick) {
                    Icon(
                        tint = LightGreen,
                        contentDescription = "Home",
                        imageVector = Icons.Default.Home,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Text(
                fontSize = 18.sp,
                color = LightGreen,
                text = "Dashboard",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )

            Box(
                modifier = Modifier.width(48.dp), contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onAddClick) {
                    Icon(
                        tint = LightGreen,
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Workout",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}