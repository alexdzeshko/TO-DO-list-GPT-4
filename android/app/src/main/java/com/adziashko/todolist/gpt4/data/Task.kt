package com.adziashko.todolist.gpt4.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adziashko.todolist.gpt4.ui.data.UIEntity

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
):UIEntity