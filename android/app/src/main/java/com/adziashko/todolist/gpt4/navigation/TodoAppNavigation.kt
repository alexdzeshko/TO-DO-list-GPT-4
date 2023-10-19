package com.adziashko.todolist.gpt4.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.adziashko.todolist.gpt4.list.TaskList
import com.adziashko.todolist.gpt4.data.Task
import com.adziashko.todolist.gpt4.edit.EditTaskScreen

@Composable
fun TodoAppNavigation(navController: NavHostController) {
    return NavHost(navController = navController, startDestination = "taskList") {
        composable("taskList") {
            TaskList { task: Task -> navController.navigate("editTask/${task.id}") }
        }
        composable(
            route = "editTask/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId")
            EditTaskScreen(
                taskId = taskId!!,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}