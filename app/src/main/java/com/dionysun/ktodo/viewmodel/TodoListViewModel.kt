package com.dionysun.ktodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dionysun.ktodo.data.TodoItem
import com.dionysun.ktodo.data.TodoItemRepository
import java.util.*
import kotlin.collections.ArrayList


class TodoListViewModel internal constructor(
    private val todoItemRepository: TodoItemRepository
): ViewModel() {

    var todoList: LiveData<List<TodoItem>> =
        todoItemRepository.getAllTodo()

    fun remove(todoItem: TodoItem){
        todoItemRepository.deleteOne(todoItem)
    }

}