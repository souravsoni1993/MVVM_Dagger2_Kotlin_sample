package com.sourav.teams.daggerexample.complete.daggerBagin.di.main

import androidx.lifecycle.ViewModel
import com.sourav.teams.daggerexample.complete.daggerBagin.di.ViewModelKey
import com.sourav.teams.daggerexample.complete.daggerBagin.ui.main.post.PostViewModel
import com.sourav.teams.daggerexample.complete.daggerBagin.ui.main.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel::class)
    abstract fun bindPostViewModel(postViewModel: PostViewModel): ViewModel
}