package com.sourav.teams.daggerexample.complete.daggerBagin.di.main

import com.sourav.teams.daggerexample.complete.daggerBagin.network.main.MainApi
import com.sourav.teams.daggerexample.complete.daggerBagin.ui.main.post.PostAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object MainModule {

    @JvmStatic
    @Provides
    fun provideMainApi(retrofit: Retrofit): MainApi {
        return retrofit.create(MainApi::class.java)
    }

    @MainScope
    @JvmStatic
    @Provides
    fun provideAdapter(): PostAdapter {
        return PostAdapter()
    }





}