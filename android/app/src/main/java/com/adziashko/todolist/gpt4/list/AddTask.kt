package com.adziashko.todolist.gpt4.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adziashko.todolist.gpt4.add.AddTaskDialog
import com.adziashko.todolist.gpt4.data.Task

@Composable
fun AddTask(
    viewModel: TaskListViewModel,
    modifier: Modifier = Modifier,
) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        AddTaskDialog(
            onAddTask = { title, description ->
                viewModel.addNewTask(Task(title = title, description = description))
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    } else {
        FloatingActionButton(
            modifier = modifier.then(Modifier.padding(16.dp)),
            onClick = { showDialog = true }
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Task")
        }
    }
}