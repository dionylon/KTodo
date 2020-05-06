package com.dionysun.ktodo.data

class TodoItemRepository private constructor(
    private val todoItemDao: TodoItemDao
){
    fun getAllTodo() = todoItemDao.getTodoItems()

    fun addTodo(vararg todo: TodoItem){
        todoItemDao.insertAll(todo.toList())
    }

    fun clearAll() = todoItemDao.clear()

    fun deleteAll(vararg todo: TodoItem) {
        todoItemDao.deleteAll(todo.toList())
    }

    fun update(vararg todo: TodoItem) {
        todoItemDao.updateAll(todo.toList())
    }

    fun deleteOne(todo: TodoItem) = todoItemDao.deleteOne(todo)

    fun findTodo(s: String) = todoItemDao.getTodoItemsLike("%$s%")

    fun findById(id: Long) = todoItemDao.findById(id)

    companion object{
        @Volatile private var instance: TodoItemRepository? = null
        fun getInstance(dao: TodoItemDao) =
            instance ?: synchronized(this){
                instance?: TodoItemRepository(dao).also { instance = it }
            }
    }
}