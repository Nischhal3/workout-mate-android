package com.example.workoutmate

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.workoutmate.model.UiEvent
import com.example.workoutmate.ui.navigation.NavGraph
import com.example.workoutmate.ui.theme.Red
import com.example.workoutmate.ui.theme.WorkoutMateTheme
import com.example.workoutmate.utils.UiEvents
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //clearDatabase(this)

        enableEdgeToEdge()
        setContent {
            WorkoutMateTheme {
                Main()
            }
        }
    }

    private fun clearDatabase(context: Context) {
        context.deleteDatabase("workout_mate.db")
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()

    var lastEvent by remember { mutableStateOf<UiEvent?>(null) }

    LaunchedEffect(Unit) {
        UiEvents.events.collect { event ->
            lastEvent = event
        }
    }

    LaunchedEffect(lastEvent) {
        if (lastEvent != null) {
            delay(1500)
            lastEvent = null
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavGraph(navController = navController, modifier = Modifier.fillMaxSize())

            val event = lastEvent
            val visible = event != null

            val message = when (event) {
                is UiEvent.ShowError -> event.message
                is UiEvent.ShowSuccess -> event.message
                else -> ""
            }

            val backgroundColor = when (event) {
                is UiEvent.ShowError -> Color(0xFFFFE8E8)
                is UiEvent.ShowSuccess -> Color(0xFFE6F4EA)
                else -> Color(0xFFE6F4EA)
            }

            val textColor = when (event) {
                is UiEvent.ShowError -> Red
                is UiEvent.ShowSuccess -> Color(0xFF1B5E20)
                else -> Color(0xFF1B5E20)
            }

            Box(modifier = Modifier.fillMaxSize()) {
                AnimatedVisibility(
                    visible = visible,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .windowInsetsPadding(WindowInsets.ime)
                        .windowInsetsPadding(WindowInsets.navigationBars),
                    enter = fadeIn() + slideInVertically { it / 2 },
                    exit = fadeOut() + slideOutVertically { it / 2 }) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        shadowElevation = 4.dp,
                        color = backgroundColor
                    ) {
                        Text(
                            text = message,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp, vertical = 12.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = textColor,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}