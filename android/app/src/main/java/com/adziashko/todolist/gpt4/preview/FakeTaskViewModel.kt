package com.adziashko.todolist.gpt4.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adziashko.todolist.gpt4.ITaskViewModel
import com.adziashko.todolist.gpt4.data.Task

class FakeTaskViewModel : ViewModel(), ITaskViewModel {
    private val _tasks = MutableLiveData(listOf(
        Task(id = 1, title = "Sample Task 1", description = "This is a sample task for preview"),
        Task(id = 2, title = "Sample Task 2", description = "This is another sample task for preview")
    ))
    override val tasks: LiveData<List<Task>> = _tasks

    override fun insert(task: Task) {
        // In the fake ViewModel, we don't need to do anything for inserting a task.
    }

    override fun update(task: Task) {
        TODO("Not yet implemented")
    }

    override fun delete(task: Task) {
        TODO("Not yet implemented")
    }

    override fun deleteCompletedTasks() {
        TODO("Not yet implemented")
    }

    override fun toggleTaskCompletionStatus(taskId: Int, isCompleted: Boolean) {
        TODO("Not yet implemented")
    }

    // ... Other ViewModel methods ...
}
