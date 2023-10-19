package com.adziashko.todolist.gpt4.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EditTaskScreen(
    viewModel: EditTaskViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    taskId: Int
) {
    val task by viewModel.task.observeAsState()

    val title = task?.title?:""
    val description = task?.description?:""

    LaunchedEffect(Unit) {
        viewModel.load(taskId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Edit Task", style = MaterialTheme.typography.h4, modifier = Modifier.padding(bottom = 16.dp))
        OutlinedTextField(
            value = title,
            onValueChange = viewModel.onTitleChange,
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = description,
            onValueChange = viewModel.onDescriptionChange,
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    viewModel.update()
                    onNavigateBack()
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Update")
            }
            Button(
                onClick = {
                    onNavigateBack()
                },
                modifier = Modifier.padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) {
                Text("Cancel")
            }
        }
    }
}
