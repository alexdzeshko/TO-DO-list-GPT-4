package com.adziashko.todolist.gpt4.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adziashko.todolist.gpt4.data.Task

@Composable
fun EditTask(
    task: Task,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onUpdate: () -> Unit,
    onCancel: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Edit Task",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        OutlinedTextField(
            value = task.title,
            onValueChange = onTitleChange,
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = task.description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 140.dp)
                .padding(bottom = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onUpdate,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Update")
            }
            Button(
                onClick = onCancel,
                modifier = Modifier.padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) {
                Text("Cancel")
            }
        }
    }
}
