package com.sourav.teams.daggerexample.complete.daggerBagin.di.auth

import androidx.lifecycle.ViewModel
import com.sourav.teams.daggerexample.complete.daggerBagin.di.ViewModelKey
import com.sourav.teams.daggerexample.complete.daggerBagin.ui.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel
}