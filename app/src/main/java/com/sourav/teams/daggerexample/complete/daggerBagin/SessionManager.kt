package com.sourav.teams.daggerexample.complete.daggerBagin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.sourav.teams.daggerexample.complete.daggerBagin.network.model.User
import com.sourav.teams.daggerexample.complete.daggerBagin.ui.auth.AuthResource
import com.sourav.teams.daggerexample.complete.daggerBagin.util.Constant.Companion.demoUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {

    private val LOG_TAG: String = SessionManager::class.simpleName!!
    private var cachedUser: MediatorLiveData<AuthResource<User>> = MediatorLiveData()

    fun authenticateWithUserId(source: LiveData<AuthResource<User>>) {
        if (cachedUser != null) {
            cachedUser.value = AuthResource.loading(demoUser)
            cachedUser.addSource(source, object : Observer<AuthResource<User>> {
                override fun onChanged(authResource: AuthResource<User>?) {
                    cachedUser.value = authResource
                    cachedUser.removeSource(source)
                }

            })
        } else {
            Log.d(
                LOG_TAG,
                "authenticateWithUserId previous session detected. Retrieving user from cache"
            )
        }

    }

    fun logOut() {
        Log.d(LOG_TAG, "logOut: logging out...")
        cachedUser.value=AuthResource.logout()
    }

    fun getAuthUser(): LiveData<AuthResource<User>> {
        return cachedUser
    }

}