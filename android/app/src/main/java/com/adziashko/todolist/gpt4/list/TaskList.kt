package com.adziashko.todolist.gpt4.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adziashko.todolist.gpt4.add.AddTaskDialog
import com.adziashko.todolist.gpt4.data.Task
import com.adziashko.todolist.gpt4.loading.LoadingScreen
import com.adziashko.todolist.gpt4.ui.data.ListHeader
import com.adziashko.todolist.gpt4.ui.data.State
import com.adziashko.todolist.gpt4.ui.data.UIEntity

@Composable
fun TaskList(viewModel: TaskListViewModel = hiltViewModel(), openTask: (task: Task) -> Unit) {
    val state by viewModel.state.observeAsState(State.Loading())
    // A surface container using the 'background' color from the theme

    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is State.Loading -> {
                LoadingScreen()
            }

            is State.Done -> {
                if (state.result.isSuccess) {
                    TaskList(
                        state.result.getOrThrow(),
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

@Composable
private fun AddTask(
    viewModel: TaskListViewModel,
    modifier: Modifier = Modifier,
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    if (showDialog) {
        AddTaskDialog(
            onAddTask = { title, description ->
                viewModel.insert(Task(title = title, description = description))
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

@Composable
fun TaskList(
    uiEntities: List<UIEntity>,
    onTaskClick: (Task) -> Unit,
    onTaskChecked: (Int, Boolean) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
        itemsIndexed(uiEntities) { index, uiEntity ->
            if (uiEntity is Task) {
                TaskItem(task = uiEntity, onTaskClick = onTaskClick,
                    onCheckboxClick = { checked -> onTaskChecked(uiEntity.id, checked) })
                if (index + 1 < uiEntities.size && uiEntities[index + 1] is Task) {
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            } else if (uiEntity is ListHeader) {
                Text(
                    text = uiEntity.title,
                    Modifier.padding(8.dp),
                    style = MaterialTheme.typography.h5
                )
            }
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
            modifier = Modifier.padding(end = 10.dp)
        )
        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable { onTaskClick(task) }) {
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