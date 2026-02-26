package com.example.workoutmate.ui.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workoutmate.model.UiEvent
import com.example.workoutmate.ui.navigation.Routes
import com.example.workoutmate.ui.screen.dashboard.components.DashboardHeader
import com.example.workoutmate.ui.screen.dashboard.components.DashboardNavBar
import com.example.workoutmate.ui.screen.dashboard.components.sessions.SessionList
import com.example.workoutmate.ui.screen.dashboard.components.sessions.details.Session
import com.example.workoutmate.ui.screen.dashboard.components.workoutform.WorkoutForm
import com.example.workoutmate.ui.theme.DarkGray
import com.example.workoutmate.ui.theme.LightGreen
import com.example.workoutmate.ui.theme.LightSage
import com.example.workoutmate.ui.theme.Red
import com.example.workoutmate.ui.theme.White
import com.example.workoutmate.viewmodel.UserViewModel

@Composable
fun Dashboard(userViewModel: UserViewModel, navController: NavController) {
    val snackBarHostState = remember { SnackbarHostState() }

    var currentEvent by remember { mutableStateOf<UiEvent?>(null) }

    val currentUser by userViewModel.currentUser.collectAsState()
    val selectedWorkoutSession by userViewModel.selectedSession.collectAsState()

    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            navController.navigate(Routes.HOME) {
                popUpTo(Routes.DASHBOARD) { inclusive = true }
            }
        }
    }
    if (currentUser == null) return

    LaunchedEffect(Unit) {
        userViewModel.events.collect { event ->
            currentEvent = event
            snackBarHostState.currentSnackbarData?.dismiss()
            snackBarHostState.showSnackbar(
                when (event) {
                    is UiEvent.ShowError -> event.message
                    is UiEvent.ShowSuccess -> event.message
                }
            )
        }
    }

    var openDialog by remember { mutableStateOf(false) }

    Scaffold(snackbarHost = {
        SnackbarHost(
            hostState = snackBarHostState, snackbar = { data ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = data.visuals.message,
                        style = MaterialTheme.typography.bodySmall,
                        color = when (currentEvent) {
                            is UiEvent.ShowError -> Red
                            is UiEvent.ShowSuccess -> DarkGray
                            else -> DarkGray
                        }
                    )
                }
            })
    }, containerColor = White, contentWindowInsets = WindowInsets(0), bottomBar = {
        DashboardNavBar(onHomeClick = {
            userViewModel.clearSelectedWorkoutSession()
        }, onAddClick = { openDialog = true })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            DashboardHeader(
                onProfileClick = { },
                onSettingsClick = { },
                username = currentUser!!.username
            )

            HorizontalDivider(color = LightSage)

            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(LightGreen)
            ) {
                if (selectedWorkoutSession == null) {
                    SessionList(userViewModel)
                } else {
                    Session(
                        userViewModel = userViewModel,
                        data = selectedWorkoutSession!!,
                        onBackClick = { userViewModel.clearSelectedWorkoutSession() })
                }
            }
        }
    }

    if (openDialog) {
        WorkoutForm(userViewModel = userViewModel, onBackClick = { openDialog = false })
    }
}