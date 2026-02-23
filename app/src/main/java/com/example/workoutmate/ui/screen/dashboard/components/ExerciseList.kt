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
import com.example.workoutmate.ui.screen.components.VerticalScrollbar
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.DarkGreen
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ExerciseList() {
    val listState = rememberLazyListState()


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
//            items([]) { day ->
//                ExerciseCard(day)
//            }
        }

        VerticalScrollbar(
            listState = listState, modifier = Modifier
                .fillMaxSize()
                .padding(end = 2.dp)
        )
    }
}

//@Composable
//fun ExerciseCard(day: WorkoutDay) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp),
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//
//            Column(
//                modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceBetween
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//
//                    Text(
//                        text = day.title,
//                        fontSize = 18.sp,
//                        color = DarkGreen,
//                        fontWeight = FontWeight.Bold,
//                    )
//
//                    Text(
//                        text = day.date.format(
//                            DateTimeFormatter.ofPattern(
//                                "dd MMM yyyy", Locale.getDefault()
//                            )
//                        ), fontSize = 14.sp, color = DarkGray
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(4.dp))
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//
//                    if (day.notes != null) {
//                        Text(
//                            text = day.notes,
//                            fontSize = 12.sp,
//                            color = DarkGreen,
//                            modifier = Modifier.weight(1f)
//                        )
//                    } else {
//                        Spacer(modifier = Modifier.weight(1f))
//                    }
//
//                    Text(
//                        text = day.date.dayOfWeek.getDisplayName(
//                            TextStyle.FULL, Locale.getDefault()
//                        ), fontSize = 12.sp, color = DarkGray
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.width(8.dp))
//
//            Icon(
//                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
//                contentDescription = null,
//                tint = DarkGreen,
//                modifier = Modifier.size(24.dp)
//            )
//        }
//    }
//}