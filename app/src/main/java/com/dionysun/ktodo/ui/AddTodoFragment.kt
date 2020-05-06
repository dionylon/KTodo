package com.dionysun.ktodo.ui

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.dionysun.ktodo.R
import com.dionysun.ktodo.databinding.FragmentAddTodoBinding
import com.dionysun.ktodo.util.InjectUtils
import com.dionysun.ktodo.viewmodel.TodoItemAddViewModel
import com.dionysun.ktodo.viewmodel.TodoItemViewModelFactory
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddTodoFragment : Fragment() {

    private val model: TodoItemAddViewModel by viewModels {
        TodoItemViewModelFactory(InjectUtils.getTodoItemRepository())
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddTodoBinding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_add_todo, container, false)
        binding.model = model
        binding.lifecycleOwner = requireActivity()
        binding.editTextDeadLine.setOnClickListener {
            val dialog = DatePickerDialog(requireContext())
            dialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                run {
                    view.minDate = Date().time
                    val date = GregorianCalendar(year, month, dayOfMonth)
                    model.todoItem.value?.deadline = date
                    model.calendar.value = date
                }
            }
            dialog.show()
        }
        binding.button.setOnClickListener {
            model.save()
            Toast.makeText(requireContext(), R.string.saveSuccess, Toast.LENGTH_SHORT).show()
            val navController = Navigation.findNavController(requireActivity(), R.id.fragmentHome)
            navController.popBackStack()
        }
        return binding.root
    }


}
