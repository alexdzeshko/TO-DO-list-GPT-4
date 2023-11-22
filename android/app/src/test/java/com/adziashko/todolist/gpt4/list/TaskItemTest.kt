package com.adziashko.todolist.gpt4.list

import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import com.adziashko.todolist.gpt4.data.Task
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TaskItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private var clicks = 0;
    private var checked = false;

    @Before
    fun setup() {
        composeTestRule.setContent {
            TaskItem(
                task = Task(0, "Title", "Description"),
                onTaskClick = { clicks++ },
                onCheckboxClick = { checked = !checked }
            )
        }
    }
    @Test
    fun taskItem_checkValues() {
        composeTestRule.onNodeWithText("Title").assertExists()
        composeTestRule.onNodeWithText("Description").assertExists()
    }

    @Test
    fun taskItem_testClick() {
        composeTestRule.onNode(isToggleable()).performClick()
        assertThat(checked).isTrue()
        assertThat(clicks).isEqualTo(0)
    }

    @Test
    fun taskItem_testCheckBox() {
        composeTestRule.onRoot().performClick()
        assertThat(clicks).isEqualTo(1)
        assertThat(checked).isFalse()
    }
}