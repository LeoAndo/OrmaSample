package com.template.reo.ormasample.data

import io.reactivex.Observable
import io.reactivex.Single

interface TodoRepository {
    fun insertIntoTodo(title: String, content: String?): Single<Long>
    fun readTodo(title: String): Single<String>
    fun updateTodo(title: String): Single<Int>
    fun deleteFromTodo(): Single<Int>
    fun transactionSync(): Single<Int>
    fun noTransactionSync(): Single<Int>
    fun rxInsertIntoTodo(title: String, content: String?): Single<Long>
    fun rxReadTodo(): Observable<String>
    fun rxUpdateTodo(title: String): Single<Int>
    fun rxDeleteFromTodo(): Single<Int>
}