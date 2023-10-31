package com.adziashko.todolist.gpt4.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.adziashko.todolist.gpt4.ITaskViewModel
import com.adziashko.todolist.gpt4.data.Task
import com.adziashko.todolist.gpt4.data.TaskDao
import com.adziashko.todolist.gpt4.ui.data.ListHeader
import com.adziashko.todolist.gpt4.ui.data.UIEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TODO = false
const val COMPLETED = true

@HiltViewModel
class TaskListViewModel @Inject constructor(private val taskDao: TaskDao) : ViewModel(),
    ITaskViewModel {
    override val tasks: LiveData<List<UIEntity>> = taskDao.getAllTasks().map { taskList ->
        val grouped = taskList.groupBy { it.isCompleted }
        val resultList = mutableListOf<UIEntity>()
        grouped[TODO]?.let {
            resultList.add(ListHeader("To-Do"))
            resultList.addAll(it)
        }
        grouped[COMPLETED]?.let {
            resultList.add(ListHeader("Completed"))
            resultList.addAll(it)
        }
        resultList
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
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.updateTaskCompletionStatus(taskId, isCompleted)
        }
    }
}
