package com.adziashko.todolist.gpt4.edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EditTaskScreen(
    viewModel: EditTaskViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    taskId: Int
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
                viewModel.update()
                onNavigateBack()
            },
            onCancel = onNavigateBack
        )
    } ?: run {
        Text(text = "Loading", modifier = Modifier.fillMaxSize())
    }
}
