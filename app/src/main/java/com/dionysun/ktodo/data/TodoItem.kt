package com.dionysun.ktodo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tb_todo")
data class TodoItem (
    @ColumnInfo(name = "content")
    var content:String,
    @ColumnInfo(name = "deadline")
    var deadline: Calendar,
    @ColumnInfo(name = "description")
    var description: String = ""
){
    constructor() : this("",GregorianCalendar())

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return content
    }
}