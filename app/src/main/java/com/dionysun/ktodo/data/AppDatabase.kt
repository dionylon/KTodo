package com.dionysun.ktodo.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dionysun.ktodo.util.MyApplication

@Database(entities = [TodoItem::class], version = 2,exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getTodoItemDao(): TodoItemDao

    companion object {
        val instance = Instance.ins
    }

    private object Instance {
        val ins: AppDatabase =
            Room.databaseBuilder(
                MyApplication.instance(),
                AppDatabase::class.java,
                "ktodo.db"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

    }
}