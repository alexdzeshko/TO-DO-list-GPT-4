package com.adziashko.todolist.gpt4

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.adziashko.todolist.gpt4.navigation.TodoAppNavigation
import com.adziashko.todolist.gpt4.ui.theme.TodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    TodoAppNavigation(navController = navController)
                }
            }
        }
    }
}

