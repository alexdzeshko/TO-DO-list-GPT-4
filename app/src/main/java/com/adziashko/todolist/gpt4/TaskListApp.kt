package com.adziashko.todolist.gpt4

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.adziashko.todolist.gpt4.add.AddTaskDialog
import com.adziashko.todolist.gpt4.data.Task

@Composable
fun TaskListApp(viewModel: ITaskViewModel, navController: NavController) {
    val tasks: State<List<Task>> = viewModel.tasks.observeAsState(emptyList())
    val showDialog = remember { mutableStateOf(false) }
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            TaskList(tasks.value, onTaskChecked = { taskId, checked ->
                viewModel.toggleTaskCompletionStatus(taskId, checked)
            }, onTaskClick = { task ->
                navController.navigate("editTask/${task.id}")
            })
            if (showDialog.value) {
                AddTaskDialog(
                    onAddTask = { title, description ->
                        viewModel.insert(Task(title = title, description = description))
                        showDialog.value = false
                    },
                    onDismiss = { showDialog.value = false }
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                FloatingActionButton(
                    onClick = { showDialog.value = true }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task")
                }
            }
        }
    }
}

@Composable
fun TaskList(
    tasks: List<Task>,
    onTaskClick: (Task) -> Unit,
    onTaskChecked: (Int, Boolean) -> Unit
) {
    LazyColumn {
        items(tasks) { task ->
            TaskItem(task = task, onTaskClick = onTaskClick,
                onCheckboxClick = { checked -> onTaskChecked(task.id, checked) })
            Divider()
        }
    }
}

@Composable
fun TaskItem(task: Task, onTaskClick: (Task) -> Unit, onCheckboxClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = onCheckboxClick,
            modifier = Modifier.padding(end = 16.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.clickable { onTaskClick(task) }) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.h6,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = task.description,
                style = MaterialTheme.typography.body1,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )
        }
    }
}