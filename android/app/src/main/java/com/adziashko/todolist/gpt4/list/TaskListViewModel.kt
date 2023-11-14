package com.adziashko.todolist.gpt4.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adziashko.todolist.gpt4.R
import com.adziashko.todolist.gpt4.data.Task
import com.adziashko.todolist.gpt4.data.TaskDao
import com.adziashko.todolist.gpt4.ui.data.ListHeader
import com.adziashko.todolist.gpt4.ui.data.UiEntity
import com.adziashko.todolist.gpt4.ui.data.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(private val taskDao: TaskDao) : ViewModel() {
    val initialState = UiState.Loading<List<Task>>()

    val uiState = taskDao.getAllTasks()
        .map { taskList ->
            val (completed, todo) = taskList.partition { it.isCompleted }
            val resultList = mutableListOf<UiEntity>()
            if (todo.isNotEmpty()) {
                resultList.add(ListHeader(R.string.list_header_todo))
                resultList.addAll(todo)
            }
            if (completed.isNotEmpty()) {
                resultList.add(ListHeader(R.string.list_header_completed))
                resultList.addAll(completed)
            }
            resultList
        }.map { result ->
            UiState.Done(Result.success(result))
        }.catch { error ->
            UiState.Done<List<UiEntity>>(Result.failure(error))
        }

    fun addNewTask(task: Task) {
        viewModelScope.launch {
            taskDao.insert(task)
        }
    }

    fun update(task: Task) {
        viewModelScope.launch {
            taskDao.update(task)
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
        }
    }

    fun deleteCompletedTasks() {
        TODO("Not yet implemented")
    }

    fun toggleTaskCompletionStatus(taskId: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            taskDao.updateTaskCompletionStatus(taskId, isCompleted)
        }
    }
}
