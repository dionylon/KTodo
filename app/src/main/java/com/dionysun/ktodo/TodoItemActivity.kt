package com.dionysun.ktodo

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.dionysun.ktodo.data.TodoItemRepository
import com.dionysun.ktodo.databinding.ActivityTodoItemBinding
import com.dionysun.ktodo.util.InjectUtils
import com.dionysun.ktodo.viewmodel.TodoItemDetailViewModel
import com.dionysun.ktodo.viewmodel.TodoItemDetailViewModelFactory
import com.dionysun.ktodo.viewmodel.TodoItemViewModelFactory
import java.util.*

class TodoItemActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTodoItemBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_todo_item)
        val id: Long = intent.extras?.get("id") as Long
        Log.d("TodoDetail", "" + id)
        val model = TodoItemDetailViewModelFactory(InjectUtils.getTodoItemRepository(), id)
                .create(TodoItemDetailViewModel::class.java)
        binding.model = model
        binding.editTextDeadLine.setOnClickListener {
            val dialog = DatePickerDialog(this)
            dialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                run {
                    view.minDate = Date().time
                    val date = GregorianCalendar(year, month, dayOfMonth)
                    model.todoItem.value?.deadline = date
                    model.date.value?.time = date.time
                }
            }
            dialog.show()
        }
        binding.imageButtonUpdate.setOnClickListener {
            model.update()
            Toast.makeText(this, R.string.saveSuccess, Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.imageButtonDelete.setOnClickListener{
            AlertDialog.Builder(this)
                .setTitle(R.string.deleteConfirm)
                .setPositiveButton(
                    R.string.yes
                ) { _, _ ->
                        model.todoItem.value?.let { it1 ->
                            InjectUtils.getTodoItemRepository().deleteOne(it1)
                        }
                        Toast.makeText(this, R.string.deleteResult, Toast.LENGTH_SHORT).show()
                        finish()
                    }
                .setNegativeButton(R.string.no) { _, _ -> }
                .create().show()
        }
        binding.lifecycleOwner = this
    }
}
