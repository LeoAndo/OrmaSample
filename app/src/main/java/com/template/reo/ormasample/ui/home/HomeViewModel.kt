package com.template.reo.ormasample.ui.home

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.reo.ormasample.domain.ApiResult
import com.template.reo.ormasample.domain.TodoService
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val todoService: TodoService
) : ViewModel() {
    val title = ObservableField<String>()
    val content = ObservableField<String>()
    val readWhere = ObservableField<String>()
    val updateWhere = ObservableField<String>()

    private val _resultInsertIntoTodo = MutableLiveData<Long>()
    val resultInsertIntoTodo: LiveData<Long> = _resultInsertIntoTodo
    private val _resultReadTodo = MutableLiveData<String>()
    val resultReadTodo: LiveData<String> = _resultReadTodo
    private val _resultUpdateTodo = MutableLiveData<Int>()
    val resultUpdateTodo: LiveData<Int> = _resultUpdateTodo
    private val _resultDeleteFromTodo = MutableLiveData<Int>()
    val resultDeleteFromTodo: LiveData<Int> = _resultDeleteFromTodo
    private val _transactionSync = MutableLiveData<Int>()
    val transactionSync: LiveData<Int> = _transactionSync
    private val _noTransactionSync = MutableLiveData<Int>()
    val noTransactionSync: LiveData<Int> = _noTransactionSync

    internal fun insertIntoTodo() {
        viewModelScope.launch {
            when (val result =
                todoService.insertIntoTodo(title.get().orEmpty(), content.get().orEmpty())) {
                is ApiResult.Success -> _resultInsertIntoTodo.value = result.value
                is ApiResult.Error -> Log.e("HomeViewModel", "error: " + result.throwable.message)
            }
        }
    }

    internal fun readTodo() {
        viewModelScope.launch {
            when (val result = todoService.readTodo(readWhere.get().orEmpty())) {
                is ApiResult.Success -> _resultReadTodo.value = result.value
                is ApiResult.Error -> Log.e("HomeViewModel", "error: " + result.throwable.message)
            }
        }
    }

    internal fun updateTodo() {
        viewModelScope.launch {
            when (val result = todoService.updateTodo(updateWhere.get().orEmpty())) {
                is ApiResult.Success -> _resultUpdateTodo.value = result.value
                is ApiResult.Error -> Log.e("HomeViewModel", "error: " + result.throwable.message)
            }
        }
    }

    internal fun deleteFromTodo() {
        viewModelScope.launch {
            when (val result = todoService.deleteFromTodo()) {
                is ApiResult.Success -> _resultDeleteFromTodo.value = result.value
                is ApiResult.Error -> Log.e("HomeViewModel", "error: " + result.throwable.message)
            }
        }
    }

    internal fun transactionSync() {
        viewModelScope.launch {
            when (val result = todoService.transactionSync()) {
                is ApiResult.Success -> _transactionSync.value = result.value
                is ApiResult.Error -> Log.e("HomeViewModel", "error: " + result.throwable.message)
            }
        }
    }

    internal fun noTransactionSync() {
        viewModelScope.launch {
            when (val result = todoService.noTransactionSync()) {
                is ApiResult.Success -> _noTransactionSync.value = result.value
                is ApiResult.Error -> Log.e("HomeViewModel", "error: " + result.throwable.message)
            }
        }
    }
}