package com.sourav.teams.daggerexample.complete.daggerBagin.di

import android.app.Application
import com.sourav.teams.daggerexample.complete.daggerBagin.BaseApplication
import com.sourav.teams.daggerexample.complete.daggerBagin.SessionManager
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuildersModule::class, AppModule::class, ViewModelFactoryModule::class])
interface AppComponent : AndroidInjector<BaseApplication> {

    /* @Component.Builder
     interface Builder {
         @BindsInstance
         fun application(application: Application): Builder
         fun build(): AppComponent
     }*/

    var sessionManager: SessionManager

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}