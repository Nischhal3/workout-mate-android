package com.example.workoutmate.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.workoutmate.ui.screen.Dashboard
import com.example.workoutmate.ui.screen.Home
import com.example.workoutmate.ui.viewmodel.UserViewModel


object Routes {
    const val HOME = "home"
    const val DASHBOARD = "dashboard"
}

@Composable
fun NavGraph(
    navController: NavHostController, userViewModel: UserViewModel, modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController, startDestination = Routes.HOME, modifier = modifier
    ) {
        composable(Routes.HOME) {
            Home(
                userViewModel = userViewModel, onLoginSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                })
        }

        composable(Routes.DASHBOARD) {
            Dashboard()
        }
    }
}