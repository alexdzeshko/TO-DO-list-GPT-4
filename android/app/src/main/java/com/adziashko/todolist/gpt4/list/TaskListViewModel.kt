package com.adziashko.todolist.gpt4.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.adziashko.todolist.gpt4.data.Task
import com.adziashko.todolist.gpt4.data.TaskDao
import com.adziashko.todolist.gpt4.ui.data.ListHeader
import com.adziashko.todolist.gpt4.ui.data.State
import com.adziashko.todolist.gpt4.ui.data.UIEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(private val taskDao: TaskDao) : ViewModel() {
    val state: LiveData<State<List<UIEntity>>> = taskDao.getAllTasks()
        //.onStart { delay(3000) }
        .map { taskList ->
            val (completed, todo) = taskList.partition { it.isCompleted }
            val resultList = mutableListOf<UIEntity>()
            if (todo.isNotEmpty()) {
                resultList.add(ListHeader("To-Do"))//todo get string from resources/or set to string res
                resultList.addAll(todo)
            }
            if (completed.isNotEmpty()) {
                resultList.add(ListHeader("Completed"))
                resultList.addAll(completed)
            }
            resultList
        }.map {result ->
            State.Done(Result.success(result))
        }.catch { error ->
            State.Done<List<UIEntity>>(Result.failure(error))
        }.asLiveData()

    fun insert(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insert(task)
        }
    }

    fun update(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.update(task)
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.delete(task)
        }
    }

    fun deleteCompletedTasks() {
        TODO("Not yet implemented")
    }

    fun toggleTaskCompletionStatus(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.updateTaskCompletionStatus(taskId, isCompleted)
        }
    }
}
