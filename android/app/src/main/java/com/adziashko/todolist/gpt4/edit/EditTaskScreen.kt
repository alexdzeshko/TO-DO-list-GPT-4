package com.adziashko.todolist.gpt4.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.adziashko.todolist.gpt4.loading.LoadingScreen

@Composable
fun EditTaskScreen(
    viewModel: EditTaskViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    taskId: Long
) {
    val task by viewModel.task.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.load(taskId)
    }

    task?.let {
        EditTask(
            task = it,
            onTitleChange = viewModel.onTitleChange,
            onDescriptionChange = viewModel.onDescriptionChange,
            onUpdate = {
                viewModel.update(onNavigateBack)
            },
            onCancel = onNavigateBack
        )
    } ?: run {
        LoadingScreen()
    }
}
