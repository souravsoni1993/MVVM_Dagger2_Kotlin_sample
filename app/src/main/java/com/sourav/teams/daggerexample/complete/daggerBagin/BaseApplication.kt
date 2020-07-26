package com.sourav.teams.daggerexample.complete.daggerBagin

import com.sourav.teams.daggerexample.complete.daggerBagin.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}