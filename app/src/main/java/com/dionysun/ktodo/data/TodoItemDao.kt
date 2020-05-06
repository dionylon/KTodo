package com.dionysun.ktodo.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoItemDao {

    @Query("SELECT * FROM tb_todo WHERE id = :id")
    fun findById(id: Long): TodoItem

    @Query("SELECT * FROM tb_todo")
    fun getTodoItems(): LiveData<List<TodoItem>>

    @Query("SELECT * FROM tb_todo WHERE content LIKE :keyword ORDER BY deadline ASC")
    fun getTodoItemsLike(keyword: String): LiveData<List<TodoItem>>

    @Insert
    fun insertAll(todoItems: List<TodoItem>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateOne(todoItem: TodoItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(todoItems: List<TodoItem>)

    @Delete
    fun deleteOne(todoItem: TodoItem)

    @Delete
    fun deleteAll(todoList: List<TodoItem>)

    @Query("DELETE FROM tb_todo")
    fun clear()
}