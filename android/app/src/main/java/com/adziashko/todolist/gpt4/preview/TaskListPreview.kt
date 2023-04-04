package com.adziashko.todolist.gpt4.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.adziashko.todolist.gpt4.TaskListApp
import com.adziashko.todolist.gpt4.ui.theme.TODOListGPT4Theme

@Preview(showBackground = true)
@Composable
fun TaskListAppPreview() {
    val navController = rememberNavController()
    TODOListGPT4Theme {
        val viewModel = FakeTaskViewModel()
        TaskListApp(viewModel = viewModel, navController = navController)
    }
}