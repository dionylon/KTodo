package com.dionysun.ktodo.ui

import android.graphics.Canvas
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.*
import com.dionysun.ktodo.R
import com.dionysun.ktodo.adapter.TodoListAdapter
import com.dionysun.ktodo.data.TodoItem
import com.dionysun.ktodo.data.TodoItemRepository
import com.dionysun.ktodo.databinding.FragmentTodoListBinding
import com.dionysun.ktodo.util.InjectUtils
import com.dionysun.ktodo.viewmodel.TodoListViewModel
import com.dionysun.ktodo.viewmodel.TodoListViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_todo_list.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class TodoListFragment : Fragment() {
    private var isLinearLayout: Boolean = true
    private val viewModel: TodoListViewModel by viewModels {
        TodoListViewModelFactory(InjectUtils.getTodoItemRepository())
    }
    private val todoItemRepository: TodoItemRepository = InjectUtils.getTodoItemRepository()
    private lateinit var adapter: TodoListAdapter
    private var deletedTodo: TodoItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentTodoListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list, container, false)
        binding.lifecycleOwner = requireActivity()
        adapter = TodoListAdapter()
        binding.recyclerView.adapter = adapter
        binding.butonAdd.setOnClickListener{
            val navController =
                Navigation.findNavController(requireActivity(), R.id.fragmentHome)
            navController.navigate(R.id.action_todoListFragment_to_addTodoFragment)
        }
        viewModel.todoList.observe(viewLifecycleOwner,
            Observer {
                adapter.submitList(it)
            })

        // 设置滑动操作
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.START or ItemTouchHelper.END
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deletedTodo = (viewHolder as TodoListAdapter.TodoItemViewHolder).binding.todoItem
                deletedTodo?.let { todoItemRepository.deleteOne(it) }
                Snackbar.make(binding.frameLayout,R.string.removeMessage, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.undo) {_-> deletedTodo?.let { todoItemRepository.addTodo(it) } }
                    .show()
            }

        }).attachToRecyclerView(binding.recyclerView)
        // 设置菜单
        setHasOptionsMenu(true)
        return  binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            // 清空全部
            R.id.item_clear -> {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.clearConfirm)
                    .setPositiveButton(
                        R.string.yes
                    ) { _, _ -> todoItemRepository.clearAll() }
                    .setNegativeButton(R.string.no) { _, _ -> }
                    .create().show()
            }
            // 切换recyclerView的视图
            R.id.app_bar_switch -> {
                isLinearLayout = !isLinearLayout
                if(isLinearLayout){
                    recyclerView.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                }else {
                    recyclerView.layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
        // 搜索
        val searchView:SearchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            // 输入值改变，更改当前的list
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { text ->
                    viewModel.todoList.removeObservers(viewLifecycleOwner)
                    viewModel.todoList = todoItemRepository.findTodo(text)
                    viewModel.todoList.observe(viewLifecycleOwner,
                    Observer{
                        adapter.submitList(it)
                    })
                }
                return true
            }

        })
    }
}
