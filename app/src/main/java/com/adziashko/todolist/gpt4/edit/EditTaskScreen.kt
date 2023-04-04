package com.adziashko.todolist.gpt4.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adziashko.todolist.gpt4.data.Task

@Composable
fun EditTaskScreen(task: Task, onUpdate: (Task) -> Unit, onNavigateBack: () -> Unit) {
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Edit Task", style = MaterialTheme.typography.h4, modifier = Modifier.padding(bottom = 16.dp))
        OutlinedTextField(
            value = title,
            onValueChange = { newValue -> title = newValue },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = description,
            onValueChange = { newValue -> description = newValue },
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
                    onUpdate(task.copy(title = title, description = description))
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
