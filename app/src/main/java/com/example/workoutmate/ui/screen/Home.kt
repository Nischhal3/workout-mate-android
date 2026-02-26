package com.example.workoutmate.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workoutmate.ui.screen.components.AppButton
import com.example.workoutmate.ui.screen.components.FlipCard
import com.example.workoutmate.ui.screen.components.InputTextField
import com.example.workoutmate.viewmodel.UserViewModel

private enum class CardSide { CREATE, LOGIN }

@Composable
fun Home(
    userViewModel: UserViewModel, onLoginSuccess: () -> Unit = {}
) {
    var side by remember { mutableStateOf(CardSide.CREATE) }

    val rotationY by animateFloatAsState(
        targetValue = if (side == CardSide.CREATE) 0f else 180f,
        animationSpec = tween(durationMillis = 550),
        label = "flip"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Spacer(Modifier.height(8.dp))

        FlipCard(rotationY = rotationY, front = {
            UserForm(
                title = "Create User",
                actionText = "Create",
                onSubmit = { username, onError, onSuccess ->
                    userViewModel.createUser(
                        username = username, onError = onError, onSuccess = onSuccess
                    )
                },
                onSuccess = { side = CardSide.LOGIN })
        }, back = {
            UserForm(
                title = "Login", actionText = "Login", onSubmit = { username, onError, onSuccess ->
                    userViewModel.loginByUsername(
                        username = username, onError = onError, onSuccess = onSuccess
                    )
                }, onSuccess = onLoginSuccess
            )
        })

        AppButton(
            modifier = Modifier.fillMaxWidth(),
            text = if (side == CardSide.CREATE) "Go to Login" else "Go to Create",
            onClick = {
                side = if (side == CardSide.CREATE) CardSide.LOGIN else CardSide.CREATE
            },
        )
    }
}

@Composable
private fun UserForm(
    title: String,
    actionText: String,
    onSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    onSubmit: (String, (String) -> Unit, () -> Unit) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge
        )

        InputTextField(
            value = username,
            onValueChange = {
                username = it
                error = null
            },
            label = "Username",
            isError = error != null,
            modifier = Modifier.fillMaxWidth(),
        )

        if (error != null) {
            Text(
                error!!,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        AppButton(
            text = actionText,
            modifier = Modifier.fillMaxWidth(),
            enabled = username.trim().isNotEmpty(),
            onClick = {
                onSubmit(username.trim(), { msg -> error = msg }, {
                    error = null
                    onSuccess()
                })
            },
        )
    }
}