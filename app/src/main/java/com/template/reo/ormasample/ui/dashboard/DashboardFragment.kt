package com.template.reo.ormasample.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.template.reo.ormasample.BaseFragment
import com.template.reo.ormasample.R
import com.template.reo.ormasample.databinding.FragmentDashboardBinding
import com.template.reo.ormasample.library.showToast
import javax.inject.Inject

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private lateinit var binding: FragmentDashboardBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DashboardViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        binding.fragment = this@DashboardFragment
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
}
