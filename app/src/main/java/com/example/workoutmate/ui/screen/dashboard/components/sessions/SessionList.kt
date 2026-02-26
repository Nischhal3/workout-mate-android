package com.example.workoutmate.ui.screen.dashboard.components.sessions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.workoutmate.ui.screen.components.SwipeToDeleteContainer
import com.example.workoutmate.ui.screen.components.VerticalScrollbar
import com.example.workoutmate.ui.theme.DividerColor
import com.example.workoutmate.utils.showToast
import com.example.workoutmate.utils.toPrettyDateString
import com.example.workoutmate.viewmodel.UserViewModel

@Composable
fun SessionList(userViewModel: UserViewModel) {
    val context = LocalContext.current
    val listState = rememberLazyListState()

    val workoutSessions by userViewModel.sessions.collectAsState()

    val groupedSessions = remember(workoutSessions) {
        workoutSessions.groupBy { it.date }
    }

    val sortedDates = remember(groupedSessions) {
        groupedSessions.keys.toList()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 10.dp),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            sortedDates.forEach { date ->
                stickyHeader {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = date.toPrettyDateString(),
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                        )

                        HorizontalDivider(
                            color = DividerColor, thickness = 1.dp
                        )
                    }
                }
                val sessionsForDate = groupedSessions[date].orEmpty()

                items(
                    items = sessionsForDate, key = { it.id }) { session ->

                    SwipeToDeleteContainer(
                        onDelete = { onError ->
                            userViewModel.deleteSessionById(sessionId = session.id, onSuccess = {
                                showToast(context, "Deleted workout session ${session.title}.")
                            }, onError = { msg ->
                                showToast(context, msg)
                                onError()
                            })
                        }) {
                        SessionItem(session = session, userViewModel = userViewModel)
                    }
                }
            }
        }

        VerticalScrollbar(
            listState = listState, modifier = Modifier
                .fillMaxSize()
                .padding(end = 2.dp)
        )
    }
}
