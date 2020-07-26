package com.sourav.teams.daggerexample.complete.daggerBagin.di

import com.sourav.teams.daggerexample.complete.daggerBagin.di.auth.AuthModule
import com.sourav.teams.daggerexample.complete.daggerBagin.di.auth.AuthScope
import com.sourav.teams.daggerexample.complete.daggerBagin.di.auth.AuthViewModelsModule
import com.sourav.teams.daggerexample.complete.daggerBagin.di.main.MainFragmentBuilderModule
import com.sourav.teams.daggerexample.complete.daggerBagin.di.main.MainModule
import com.sourav.teams.daggerexample.complete.daggerBagin.di.main.MainScope
import com.sourav.teams.daggerexample.complete.daggerBagin.di.main.MainViewModelModule
import com.sourav.teams.daggerexample.complete.daggerBagin.ui.main.MainActivity
import com.sourav.teams.daggerexample.complete.daggerBagin.ui.auth.AuthActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(modules = [AuthViewModelsModule::class, AuthModule::class])
    abstract fun contributeAuthActivity(): AuthActivity

    @MainScope
    @ContributesAndroidInjector(modules = [MainFragmentBuilderModule::class, MainViewModelModule::class, MainModule::class])
    abstract fun contributeMainActivity(): MainActivity
}