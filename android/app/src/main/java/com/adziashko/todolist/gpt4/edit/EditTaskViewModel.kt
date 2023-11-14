package com.adziashko.todolist.gpt4.edit

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.adziashko.todolist.gpt4.data.Task
import com.adziashko.todolist.gpt4.data.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(private val taskDao: TaskDao) : ViewModel() {

    val task: MediatorLiveData<Task> = MediatorLiveData()
    val onTitleChange: (String) -> Unit = {
        task.value = task.value?.copy(title = it)
    }
    val onDescriptionChange: (String) -> Unit = {
        task.value = task.value?.copy(description = it)
    }

    fun load(taskId: Long) {
        task.addSource(taskDao.getTask(taskId).asLiveData()) { value ->
            run {
                task.value = value
            }
        }
    }

    fun update(onComplete: () -> Unit) {
        viewModelScope.launch {
            taskDao.update(task.value!!)
            onComplete()
        }
    }

}