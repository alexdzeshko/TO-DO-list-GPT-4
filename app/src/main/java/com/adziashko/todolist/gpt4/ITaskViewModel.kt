package com.adziashko.todolist.gpt4

import androidx.lifecycle.LiveData
import com.adziashko.todolist.gpt4.data.Task

interface ITaskViewModel {
    val tasks: LiveData<List<Task>>

    fun insert(task: Task)

    fun update(task: Task)

    fun delete(task: Task)

    fun deleteCompletedTasks()

    fun toggleTaskCompletionStatus(taskId: Int, isCompleted: Boolean)
}
