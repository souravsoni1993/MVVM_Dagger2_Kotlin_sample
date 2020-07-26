package com.sourav.teams.daggerexample.complete.daggerBagin.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.sourav.teams.daggerexample.complete.daggerBagin.SessionManager
import com.sourav.teams.daggerexample.complete.daggerBagin.network.auth.AuthApi
import com.sourav.teams.daggerexample.complete.daggerBagin.network.model.User
import com.sourav.teams.daggerexample.complete.daggerBagin.util.Constant.Companion.demoUser
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class AuthViewModel @Inject constructor(private var authApi: AuthApi, private val sessionManager: SessionManager) : ViewModel() {

    private val LOG_TAG: String = AuthViewModel::class.simpleName!!

    internal fun getUserById(userId: Int) {
        Log.d(LOG_TAG, "attempting to login")
        sessionManager.authenticateWithUserId(queryUserId(userId))
    }

    private fun queryUserId(userId: Int): LiveData<AuthResource<User>> {
        return LiveDataReactiveStreams.fromPublisher<AuthResource<User>>(
            authApi.getUser(userId) // instead of calling onError, do this
                .onErrorReturn(object : Function<Throwable?, User?> {
                    @Throws(Exception::class)
                    override fun apply(throwable: Throwable): User? {
                        val errorUser = User(null, null, null, -1, null, null, null, null)
                        errorUser.id = -1
                        return errorUser
                    }
                }) // wrap User object in AuthResource
                .map(object :
                    Function<User?, AuthResource<User>> {
                    @Throws(Exception::class)
                    override fun apply(user: User): AuthResource<User> {
                        return if (user.id == -1) {
                            AuthResource.error("Could not authenticate", demoUser)
                        } else AuthResource.authenticated(user)
                    }
                })
                .subscribeOn(Schedulers.io())
        )

    }

    internal fun observeAuthState(): LiveData<AuthResource<User>> {
        return sessionManager.getAuthUser()
    }


}

