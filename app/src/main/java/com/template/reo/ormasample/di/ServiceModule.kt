package com.template.reo.ormasample.di

import com.template.reo.ormasample.domain.TodoService
import com.template.reo.ormasample.domain.TodoServiceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ServiceModule {
    @Binds
    abstract fun bindTodoService(impl: TodoServiceImpl): TodoService
}