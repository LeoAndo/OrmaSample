package com.template.reo.ormasample.data

import com.github.gfx.android.orma.Inserter
import com.template.reo.ormasample.orma.OrmaDatabase
import com.template.reo.ormasample.orma.Todo
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class TodoDataSource @Inject constructor(
    private val ormaDatabase: OrmaDatabase
) : TodoRepository {
    override fun insertIntoTodo(title: String, content: String?): Single<Long> {
        return Single.create { emitter ->
            // 単発でINSERTするだけならこれで
            try {
                val lastInsertedRowId = ormaDatabase.insertIntoTodo(Todo.create(title, content))
                emitter.onSuccess(lastInsertedRowId)
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    override fun readTodo(title: String): Single<String> {
        return Single.create { emitter ->
            try {
                val result = StringBuilder()
                for (todo in ormaDatabase.selectFromTodo().titleEq(title)) {
                    result.append("selectFromTodo: ").append(todo.id).append("\n")
                }
                emitter.onSuccess(result.toString())
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    override fun updateTodo(title: String): Single<Int> {
        return Single.create { emitter ->
            try {
                val updateCount = ormaDatabase.updateTodo().titleEq(title).done(true).execute()
                emitter.onSuccess(updateCount)
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    override fun deleteFromTodo(): Single<Int> {
        return Single.create { emitter ->
            try {
                val deleteCount = ormaDatabase.deleteFromTodo().doneEq(true).execute()
                emitter.onSuccess(deleteCount)
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    override fun transactionSync(): Single<Int> {
        return Single.create { emitter ->
            try {
                ormaDatabase.transactionSync {
                    // この区間を実行中、他の要求は一時停止されます
                    val now = System.currentTimeMillis()
                    val statement: Inserter<Todo> = ormaDatabase.prepareInsertIntoTodo()
                    for (i in 0 until 5) {
                        val todo = Todo()
                        todo.title = "transactionSync title: $i"
                        todo.memo = "transactionSync memo: $i"
                        todo.createdTime = Date(now)
                        statement.execute(todo)
                        Thread.sleep(1000)
                    }
                    emitter.onSuccess(0)
                }
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    override fun noTransactionSync(): Single<Int> {
        return Single.create { emitter ->
            try {
                val now = System.currentTimeMillis()
                val statement: Inserter<Todo> = ormaDatabase.prepareInsertIntoTodo()
                for (i in 0 until 5) {
                    val todo = Todo()
                    todo.title = "noTransactionSync title: $i"
                    todo.memo = "noTransactionSync memo: $i"
                    todo.createdTime = Date(now)
                    statement.execute(todo)
                    Thread.sleep(1000)
                }
                emitter.onSuccess(0)
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    override fun rxInsertIntoTodo(title: String, content: String?): Single<Long> {
        return ormaDatabase.prepareInsertIntoTodoAsSingle().flatMap {
            it.executeAsSingle(Todo.create(title, content))
        }
    }

    override fun rxReadTodo(): Observable<String> {
        return ormaDatabase.selectFromTodo().executeAsObservable().map {
            val result = StringBuilder()
            result.append("rx select: ").append(it.id).append("\n")
            result.toString()
        }
    }

    override fun rxUpdateTodo(title: String): Single<Int> {
        return ormaDatabase.updateTodo().titleEq(title).done(true).executeAsSingle()
    }

    override fun rxDeleteFromTodo(): Single<Int> {
        return ormaDatabase.deleteFromTodo().doneEq(true).executeAsSingle()
    }
}