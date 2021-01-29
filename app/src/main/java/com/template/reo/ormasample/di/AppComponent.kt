package com.template.reo.ormasample.di

import android.app.Application
import com.template.reo.ormasample.MyApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        AppModule::class,
        DaoModule::class,
        FragmentModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        DaoModule::class,
        ServiceModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}