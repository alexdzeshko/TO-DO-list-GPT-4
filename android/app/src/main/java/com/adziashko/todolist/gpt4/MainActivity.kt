package com.adziashko.todolist.gpt4

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.adziashko.todolist.gpt4.edit.EditTaskScreen
import com.adziashko.todolist.gpt4.ui.theme.TODOListGPT4Theme

class MainActivity : AppCompatActivity() {
    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TODOListGPT4Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "taskList") {
                    composable("taskList") {
                        TaskListApp(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                    composable(
                        route = "editTask/{taskId}",
                        arguments = listOf(navArgument("taskId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getInt("taskId")
                        val task = taskId?.let { id ->
                            viewModel.tasks.value?.firstOrNull { task -> task.id == id }
                        }

                        if (task != null) {
                            EditTaskScreen(
                                task = task,
                                onUpdate = { updatedTask ->
                                    viewModel.update(updatedTask)
                                    navController.popBackStack()
                                },
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        } else {
                            Text("Task not found")
                        }
                    }
                }
            }
        }
    }
}

