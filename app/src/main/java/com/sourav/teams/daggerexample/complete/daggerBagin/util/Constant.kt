package com.sourav.teams.daggerexample.complete.daggerBagin.util

import com.sourav.teams.daggerexample.complete.daggerBagin.network.model.User

class Constant {
    companion object {
        const val BASE_URL: String = "https://jsonplaceholder.typicode.com"

         val demoUser = User(
            address = null,
            company = null,
            email = null,
            id = null,
            name = null,
            phone = null,
            username = null,
            website = null
        )
    }


}