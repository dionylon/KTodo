package com.dionysun.ktodo.util

import com.dionysun.ktodo.data.AppDatabase
import com.dionysun.ktodo.data.TodoItemRepository

object InjectUtils {
    fun getTodoItemRepository(): TodoItemRepository {
        return TodoItemRepository.getInstance(AppDatabase.instance.getTodoItemDao())
    }
}