package com.sourav.teams.daggerexample.complete.daggerBagin.di

import androidx.lifecycle.ViewModelProvider
import com.sourav.teams.daggerexample.complete.daggerBagin.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}