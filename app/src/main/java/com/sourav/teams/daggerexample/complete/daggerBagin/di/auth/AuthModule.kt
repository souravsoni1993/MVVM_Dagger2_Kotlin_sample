package com.sourav.teams.daggerexample.complete.daggerBagin.di.auth

import com.sourav.teams.daggerexample.complete.daggerBagin.network.auth.AuthApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object AuthModule {

    @AuthScope
    @JvmStatic
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}