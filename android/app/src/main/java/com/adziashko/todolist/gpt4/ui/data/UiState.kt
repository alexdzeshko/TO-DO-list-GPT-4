package com.adziashko.todolist.gpt4.ui.data


sealed interface UiState<out T> {
    val result: Result<T>

    data class Done<T>(override val result: Result<T>) : UiState<T>

    @JvmInline
    value class Loading<T>(override val result: Result<T> = Result.failure(Throwable("Loading"))) :
        UiState<T>
}