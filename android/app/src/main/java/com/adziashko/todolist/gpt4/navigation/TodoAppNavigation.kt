package com.adziashko.todolist.gpt4.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.adziashko.todolist.gpt4.data.Task
import com.adziashko.todolist.gpt4.edit.EditTaskScreen
import com.adziashko.todolist.gpt4.list.TaskList

@Composable
fun TodoAppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "taskList") {
        composable("taskList") {
            TaskList { task: Task -> navController.navigate("editTask/${task.id}") }
        }
        composable(
            route = "editTask/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.LongType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId")
            EditTaskScreen(
                taskId = taskId!!,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}