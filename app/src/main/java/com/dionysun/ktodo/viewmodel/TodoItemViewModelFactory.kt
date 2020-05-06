package com.dionysun.ktodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dionysun.ktodo.data.TodoItemRepository

class TodoItemViewModelFactory(
    private val repository: TodoItemRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoItemAddViewModel(repository) as T
    }
}