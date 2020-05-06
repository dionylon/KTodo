package com.dionysun.ktodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dionysun.ktodo.data.TodoItem
import com.dionysun.ktodo.data.TodoItemRepository

class TodoItemDetailViewModelFactory(
    private val repository: TodoItemRepository,
    private  val id: Long
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoItemDetailViewModel(repository, id) as T
    }
}