package com.sourav.teams.daggerexample.complete.daggerBagin.network.main

import com.sourav.teams.daggerexample.complete.daggerBagin.network.model.PostItem
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {
    @GET("posts")
    fun getUserSpecificPost(@Query("userId") userId: Int): Flowable<List<PostItem>>
}