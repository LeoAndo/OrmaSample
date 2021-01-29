package com.template.reo.ormasample.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.template.reo.ormasample.BaseFragment
import com.template.reo.ormasample.R
import com.template.reo.ormasample.databinding.FragmentHomeBinding
import com.template.reo.ormasample.library.showToast
import javax.inject.Inject

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.fragment = this@HomeFragment
        binding.viewModel = viewModel

        viewModel.resultInsertIntoTodo.observe(
            viewLifecycleOwner,
            Observer { showToast(it.toString()) })
        viewModel.resultReadTodo.observe(viewLifecycleOwner, Observer { showToast(it) })
        viewModel.resultUpdateTodo.observe(
            viewLifecycleOwner,
            Observer { showToast(it.toString()) })
        viewModel.resultDeleteFromTodo.observe(
            viewLifecycleOwner,
            Observer { showToast(it.toString()) })
        viewModel.transactionSync.observe(
            viewLifecycleOwner,
            Observer { showToast(it.toString()) })
        viewModel.noTransactionSync.observe(
            viewLifecycleOwner,
            Observer { showToast(it.toString()) })
    }

    fun onClickButtonCreate() {
        viewModel.insertIntoTodo()
    }

    fun onClickButtonRead() {
        viewModel.readTodo()
    }

    fun onClickButtonUpdate() {
        viewModel.updateTodo()
    }

    fun onClickButtonDelete() {
        viewModel.deleteFromTodo()
    }

    fun onClickButtonEnableTransactionSync() {
        viewModel.transactionSync()
    }

    fun onClickButtonNoTransactionSync() {
        viewModel.noTransactionSync()
    }

}
