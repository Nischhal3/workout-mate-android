package com.example.workoutmate.ui.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.workoutmate.ui.navigation.Routes
import com.example.workoutmate.ui.screen.dashboard.components.DashboardHeader
import com.example.workoutmate.ui.screen.dashboard.components.DashboardNavBar
import com.example.workoutmate.ui.screen.dashboard.components.ExerciseList
import com.example.workoutmate.ui.screen.dashboard.components.workoutform.WorkoutForm
import com.example.workoutmate.ui.theme.LightGreen
import com.example.workoutmate.ui.theme.LightSage
import com.example.workoutmate.ui.theme.White
import com.example.workoutmate.ui.viewmodel.UserViewModel

@Composable
fun Dashboard(userViewModel: UserViewModel, navController: NavController) {
    val currentUser by userViewModel.currentUser.collectAsState()

    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            navController.navigate(Routes.HOME) {
                popUpTo(Routes.DASHBOARD) { inclusive = true }
            }
        }
    }
    if (currentUser == null) return

    var openDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = White, bottomBar = {
            DashboardNavBar(onHomeClick = { }, onAddClick = { openDialog = true })
        }) { _ ->
        Column(
            modifier = Modifier.fillMaxSize()
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
                ExerciseList()
            }
        }
    }

    if (openDialog) {
        WorkoutForm(onBackClick = { openDialog = false })
    }
}