package com.template.reo.ormasample.di

import com.template.reo.ormasample.data.TodoDataSource
import com.template.reo.ormasample.data.TodoRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindTodoRepository(dataSource: TodoDataSource): TodoRepository
}