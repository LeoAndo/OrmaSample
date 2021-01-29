package com.template.reo.ormasample.domain

import com.template.reo.ormasample.data.TodoRepository
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.rx2.await
import javax.inject.Inject

interface TodoService {
    suspend fun insertIntoTodo(title: String, content: String?): ApiResult<Long>
    suspend fun readTodo(title: String): ApiResult<String>
    suspend fun updateTodo(title: String): ApiResult<Int>
    suspend fun deleteFromTodo(): ApiResult<Int>
    suspend fun transactionSync(): ApiResult<Int>
    suspend fun noTransactionSync(): ApiResult<Int>
    fun rxInsertIntoTodo(title: String, content: String?): Single<Long>
    fun rxReadTodo(): Observable<String>
    fun rxUpdateTodo(title: String): Single<Int>
    fun rxDeleteFromTodo(): Single<Int>
}

class TodoServiceImpl @Inject constructor(
    private val todoRepository: TodoRepository
) : TodoService {
    override suspend fun insertIntoTodo(title: String, content: String?): ApiResult<Long> {
        return safeApiCall { todoRepository.insertIntoTodo(title, content).await() }
    }

    override suspend fun readTodo(title: String): ApiResult<String> {
        return safeApiCall { todoRepository.readTodo(title).await() }
    }

    override suspend fun updateTodo(title: String): ApiResult<Int> {
        return safeApiCall { todoRepository.updateTodo(title).await() }
    }

    override suspend fun deleteFromTodo(): ApiResult<Int> {
        return safeApiCall { todoRepository.deleteFromTodo().await() }
    }

    override suspend fun transactionSync(): ApiResult<Int> {
        return safeApiCall { todoRepository.transactionSync().await() }
    }

    override suspend fun noTransactionSync(): ApiResult<Int> {
        return safeApiCall { todoRepository.noTransactionSync().await() }
    }

    override fun rxInsertIntoTodo(title: String, content: String?): Single<Long> {
        return todoRepository.rxInsertIntoTodo(title, content)
    }

    override fun rxReadTodo(): Observable<String> {
        return todoRepository.rxReadTodo()
    }

    override fun rxUpdateTodo(title: String): Single<Int> {
        return todoRepository.rxUpdateTodo(title)
    }

    override fun rxDeleteFromTodo(): Single<Int> {
        return todoRepository.rxDeleteFromTodo()
    }
}