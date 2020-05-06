package com.dionysun.ktodo.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dionysun.ktodo.TodoItemActivity
import com.dionysun.ktodo.data.TodoItem
import com.dionysun.ktodo.databinding.CeilCardBinding
import java.util.*

class TodoListAdapter: ListAdapter<TodoItem, RecyclerView.ViewHolder>(TodoItemDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TodoItemViewHolder(
            CeilCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val todo=getItem(position)
        (holder as TodoItemViewHolder).bind(todo)
    }

    class TodoItemViewHolder(
        val binding: CeilCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TodoItem){
            binding.apply {
                todoItem = item
                listener = View.OnClickListener {
                    // 启动详情页的activity,传入点击的item的id
                    val intent = Intent()
                    intent.setClass(binding.root.context, TodoItemActivity::class.java)
                    intent.putExtra("id",item.id)
                    binding.root.context.startActivity(intent)
                }
                executePendingBindings()
            }
        }
    }

}

private class TodoItemDiffCallBack: DiffUtil.ItemCallback<TodoItem>(){
    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return Objects.equals(oldItem.id, newItem.id)
    }

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return Objects.equals(oldItem, newItem)
    }

}