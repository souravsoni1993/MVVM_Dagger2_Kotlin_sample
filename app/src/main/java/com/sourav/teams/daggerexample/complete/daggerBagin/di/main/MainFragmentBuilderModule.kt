package com.sourav.teams.daggerexample.complete.daggerBagin.di.main

import com.sourav.teams.daggerexample.complete.daggerBagin.ui.main.post.PostFragment
import com.sourav.teams.daggerexample.complete.daggerBagin.ui.main.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributePostFragment(): PostFragment
}