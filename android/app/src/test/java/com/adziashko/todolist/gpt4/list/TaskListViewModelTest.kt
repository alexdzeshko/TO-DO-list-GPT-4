package com.adziashko.todolist.gpt4.list

import com.adziashko.todolist.gpt4.MainDispatcherRule
import com.adziashko.todolist.gpt4.R
import com.adziashko.todolist.gpt4.data.Task
import com.adziashko.todolist.gpt4.data.TaskDao
import com.adziashko.todolist.gpt4.ui.data.ListHeader
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class TaskListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var taskDao: TaskDao

    private lateinit var viewModel: TaskListViewModel

    @Before
    fun init() {
        hiltRule.inject()
        viewModel = TaskListViewModel(taskDao)
    }

    @Test
    fun addNewTask() = runTest {
        val task = Task(title = "Test title", description = "Test desc", isCompleted = true)

        viewModel.addNewTask(task)
        advanceUntilIdle()

        viewModel.uiState.first().apply {
            assertThat(result.isSuccess).isTrue()
            with(result.getOrThrow()) {
                assertThat(this).hasSize(2)
                assertThat(first()).isEqualTo(ListHeader(R.string.list_header_completed))
                with(get(1) as Task){
                    assertThat(title).isEqualTo("Test title")
                    assertThat(description).isEqualTo("Test desc")
                }
            }
        }
    }
}