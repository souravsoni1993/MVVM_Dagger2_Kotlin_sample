package com.sourav.teams.daggerexample.complete.daggerBagin.ui.main.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sourav.teams.daggerexample.complete.daggerBagin.SessionManager
import com.sourav.teams.daggerexample.complete.daggerBagin.network.model.User
import com.sourav.teams.daggerexample.complete.daggerBagin.ui.auth.AuthResource
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val sessionManager: SessionManager) :
    ViewModel() {

    private val LOG_TAG: String = ProfileViewModel::class.simpleName!!

    fun observeUserDetails(): LiveData<AuthResource<User>> {
        Log.d(LOG_TAG,"observing user details")
        return sessionManager.getAuthUser()
    }

}