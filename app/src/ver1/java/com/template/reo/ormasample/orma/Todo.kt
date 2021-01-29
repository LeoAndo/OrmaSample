package com.template.reo.ormasample.orma

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Table
import com.template.reo.ormasample.orma.Todo.Companion.TABLE_NAME
import java.util.*

/**
 * Ormaでは、テーブルに対応するスキーマはPOJOにアノテーションを付加したものです。
 * Ormaのannotation processorがアノテーションをみてヘルパークラスを生成します。
 * スキーマを定義するには、次のようにモデルクラスを@Table, @PrimaryKey, @Colum アノテーションで注釈します。
 * カラムはデフォルトで NOT NULL になるので、NULLを許容する場合は @Nullable アノテーションが必要だということに注意してください。
 */
@Table(TABLE_NAME)
class Todo {

    @PrimaryKey(autoincrement = true)
    var id: Long = 0

    @Column(indexed = true)
    var title: String = ""

    @Column(value = "content")
    var memo: String? = null

    @Column(indexed = true, defaultExpr = "0")
    var done: Boolean = false

    @Column(indexed = true, helpers = Column.Helpers.ORDERS, defaultExpr = "0")
    var createdTime: Date? = null

    companion object {
        fun create(title: String, content: String?): Todo {
            val todo = Todo()
            todo.title = title
            todo.memo = content
            todo.createdTime = Date()
            return todo
        }

        const val TABLE_NAME = "Todo"
    }
}