package com.adziashko.todolist.gpt4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.adziashko.todolist.gpt4.data.Task
import com.adziashko.todolist.gpt4.data.TaskDao
import com.adziashko.todolist.gpt4.data.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application), ITaskViewModel {
    private val taskDao: TaskDao = TaskDatabase.getDatabase(application).taskDao()
    override val tasks: LiveData<List<Task>> = taskDao.getAllTasks().map { taskList ->
        taskList.sortedBy { it.isCompleted }
    }.asLiveData()

    override fun insert(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insert(task)
        }
    }

    override fun update(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.update(task)
        }
    }

    override fun delete(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.delete(task)
        }
    }

    override fun deleteCompletedTasks() {
        TODO("Not yet implemented")
    }

    override fun toggleTaskCompletionStatus(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            taskDao.updateTaskCompletionStatus(taskId, isCompleted)
        }
    }
}
