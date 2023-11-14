package com.adziashko.todolist.gpt4.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.adziashko.todolist.gpt4.data.Task

@Composable
fun TaskItem(
    task: Task,
    onTaskClick: (Task) -> Unit,
    onCheckboxClick: (Boolean) -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier
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