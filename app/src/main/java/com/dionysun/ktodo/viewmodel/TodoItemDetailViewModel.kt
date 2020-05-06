package com.dionysun.ktodo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dionysun.ktodo.data.TodoItem
import com.dionysun.ktodo.data.TodoItemRepository
import java.util.*

class TodoItemDetailViewModel (
    private val todoItemRepository: TodoItemRepository,
    private val id: Long
): ViewModel()  {
    val todoItem: MutableLiveData<TodoItem> = MutableLiveData()
    val date: MutableLiveData<Calendar> = MutableLiveData()

    init {
        val todo = todoItemRepository.findById(id)
        Log.d("TodoDetailVM",todo.toString())
        date.value = todo.deadline
        todoItem.value = todo
    }
    fun update() {
        todoItem.value?.let { todoItemRepository.update(it) }
    }
}