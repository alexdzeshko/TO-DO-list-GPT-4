package com.adziashko.todolist.gpt4.ui.data

import androidx.annotation.StringRes

@JvmInline
value class ListHeader(@StringRes val titleRes: Int):UiEntity {
    override val id: Long
        get() = titleRes.toLong()
}
