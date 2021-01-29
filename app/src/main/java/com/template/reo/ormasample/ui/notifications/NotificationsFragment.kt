package com.template.reo.ormasample.ui.notifications

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.gfx.android.orma.rx.RxRelation
import com.github.gfx.android.orma.widget.OrmaRecyclerViewAdapter
import com.template.reo.ormasample.BaseFragment
import com.template.reo.ormasample.R
import com.template.reo.ormasample.databinding.CardTodoBinding
import com.template.reo.ormasample.databinding.FragmentNotificationsBinding
import com.template.reo.ormasample.orma.OrmaDatabase
import com.template.reo.ormasample.orma.Todo
import com.template.reo.ormasample.orma.Todo_Relation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class NotificationsFragment : BaseFragment(R.layout.fragment_notifications) {

    private var number: Int = 0
    private lateinit var adapter: MyAdapter
    private lateinit var binding: FragmentNotificationsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: NotificationsViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var ormaDatabase: OrmaDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotificationsBinding.bind(view)

        adapter = MyAdapter(requireContext(), ormaDatabase.relationOfTodo().orderByCreatedTimeAsc())
        binding.list.adapter = adapter

        binding.fab.setOnClickListener {
            adapter.addItemAsSingle {
                val todo = Todo()
                number++
                todo.title = "RecyclerView item #$number"
                todo.memo = Date().time.toString()
                todo.createdTime = Date()
                todo
            }.subscribeOn(Schedulers.io()).subscribe()
        }
    }

    private class MyViewHolder(parent: ViewGroup, layoutInflater: LayoutInflater) :
        RecyclerView.ViewHolder(
            CardTodoBinding.inflate(layoutInflater, parent, false).root
        ) {
        val binding: CardTodoBinding = DataBindingUtil.getBinding(itemView)!!
    }

    private class MyAdapter(context: Context, relation: RxRelation<Todo, *>) :
        OrmaRecyclerViewAdapter<Todo, MyViewHolder>(context, relation) {
        override fun getRelation(): Todo_Relation {
            return super.getRelation() as Todo_Relation
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(parent, layoutInflater)
        }

        override fun onBindViewHolder(
            holder: MyViewHolder,
            position: Int
        ) {
            val binding: CardTodoBinding = holder.binding
            val todo = getItem(position)
            Log.d("MyAdapter", "onBindViewHolder position: $position")
            binding.title.text = todo.title
            binding.content.text = todo.memo
            setStrike(binding.title, todo.done)
            binding.root.setOnClickListener { v ->
                val currentTodo: Todo = relation.reload(todo)
                val done = !currentTodo.done
                relation
                    .updater()
                    .idEq(todo.id)
                    .done(done)
                    .executeAsSingle()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { _ ->
                        // unlike ListViewAdapter, RecyclerViewAdapter can notify single item changed
                        notifyItemChanged(position)
                    }
            }
            binding.root.setOnLongClickListener { v ->
                removeItemAsMaybe(todo)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
                true
            }
        }

        fun setStrike(textView: TextView, value: Boolean) {
            if (value) {
                textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }
}
