package com.adziashko.todolist.gpt4.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.adziashko.todolist.gpt4.data.Task
import com.adziashko.todolist.gpt4.loading.LoadingScreen
import com.adziashko.todolist.gpt4.ui.data.ListHeader
import com.adziashko.todolist.gpt4.ui.data.UiEntity
import com.adziashko.todolist.gpt4.ui.data.UiState

@Composable
fun TaskList(viewModel: TaskListViewModel = hiltViewModel(), openTask: (task: Task) -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(viewModel.initialState)
    // A surface container using the 'background' color from the theme

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen()
            }

            is UiState.Done -> {
                if (uiState.result.isSuccess) {
                    TaskList(
                        uiState.result.getOrThrow(),
                        onTaskChecked = { taskId, checked ->
                            viewModel.toggleTaskCompletionStatus(taskId, checked)
                        },
                        onTaskClick = openTask
                    )
                } else {
                    // todo error view
                }
                AddTask(viewModel, modifier = Modifier.align(Alignment.BottomEnd))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskList(
    uiEntities: List<UiEntity>,
    onTaskClick: (Task) -> Unit,
    onTaskChecked: (Long, Boolean) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 64.dp)
    ) {
        itemsIndexed(uiEntities, key = { _, item -> item.id }) { index, uiEntity ->
            if (uiEntity is Task) {
                TaskItem(
                    task = uiEntity,
                    onTaskClick = onTaskClick,
                    onCheckboxClick = { checked -> onTaskChecked(uiEntity.id, checked) },
                    Modifier.animateItemPlacement()
                )
                if (index + 1 < uiEntities.size && uiEntities[index + 1] is Task) {
                    Divider(
                        modifier = Modifier
                            .animateItemPlacement()
                            .padding(horizontal = 16.dp)
                    )
                }
            } else if (uiEntity is ListHeader) {
                Text(
                    text = stringResource(uiEntity.titleRes),
                    Modifier
                        .animateItemPlacement()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}