package com.template.reo.ormasample.ui.dashboard

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.template.reo.ormasample.domain.TodoService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.disposables.SerialDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
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

    private val disposableCreate = SerialDisposable(Disposables.empty())
    private val disposableRead = SerialDisposable(Disposables.empty())
    private val disposableUpdate = SerialDisposable(Disposables.empty())
    private val disposableDelete = SerialDisposable(Disposables.empty())

    override fun onCleared() {
        super.onCleared()
        disposableCreate.set(Disposables.empty())
        disposableRead.set(Disposables.empty())
        disposableUpdate.set(Disposables.empty())
        disposableDelete.set(Disposables.empty())
    }

    internal fun insertIntoTodo() {
        disposableCreate.set(
            todoService.rxInsertIntoTodo(title.get().orEmpty(), content.get().orEmpty())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _resultInsertIntoTodo.value = it
                }, {
                    Log.e("HomeViewModel", "error: $it")
                })
        )
    }

    internal fun readTodo() {
        disposableRead.set(
            todoService.rxReadTodo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _resultReadTodo.value = it
                }, {
                    Log.e("HomeViewModel", "error: $it")
                })
        )
    }

    internal fun updateTodo() {
        disposableUpdate.set(
            todoService.rxUpdateTodo(title.get().orEmpty())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _resultUpdateTodo.value = it
                }, {
                    Log.e("HomeViewModel", "error: $it")
                })
        )
    }

    internal fun deleteFromTodo() {
        disposableDelete.set(
            todoService.rxDeleteFromTodo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _resultDeleteFromTodo.value = it
                }, {
                    Log.e("HomeViewModel", "error: $it")
                })
        )
    }
}