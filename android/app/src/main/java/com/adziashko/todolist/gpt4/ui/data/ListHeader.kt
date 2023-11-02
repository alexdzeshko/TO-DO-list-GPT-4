package com.adziashko.todolist.gpt4.ui.data

@JvmInline
value class ListHeader(val title: String):UIEntity {
    override val id: Long
        get() = title.hashCode().toLong()
}
