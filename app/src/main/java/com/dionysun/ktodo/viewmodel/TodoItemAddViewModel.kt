package com.dionysun.ktodo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dionysun.ktodo.data.TodoItem
import com.dionysun.ktodo.data.TodoItemRepository
import java.util.*

class TodoItemAddViewModel internal constructor(
    private val todoItemRepository: TodoItemRepository
): ViewModel()  {
    val todoItem: MutableLiveData<TodoItem> = MutableLiveData(TodoItem())
    val calendar: MutableLiveData<Calendar> = MutableLiveData(Calendar.getInstance())
    fun save() {
        val todo: TodoItem? = todoItem.value
        todo?.let { todoItemRepository.addTodo(it) }
    }
    fun update() {
        todoItem.value?.let { todoItemRepository.update(it) }
    }
}