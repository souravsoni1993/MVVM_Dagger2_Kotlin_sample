package com.sourav.teams.daggerexample.complete.daggerBagin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.sourav.teams.daggerexample.complete.daggerBagin.network.model.User
import com.sourav.teams.daggerexample.complete.daggerBagin.ui.auth.AuthActivity
import com.sourav.teams.daggerexample.complete.daggerBagin.ui.auth.AuthResource
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

open class BaseActivity : DaggerAppCompatActivity() {

    private val LOG_TAG: String = BaseActivity::class.simpleName!!

    @JvmField
    @Inject
    var sessionManager: SessionManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeSessionManagerState()

    }

    private fun subscribeSessionManagerState() {
        sessionManager?.getAuthUser()?.observe(this, object : Observer<AuthResource<User>> {
            override fun onChanged(authResource: AuthResource<User>?) {
                if (authResource != null) {
                    when (authResource.status) {
                        AuthResource.AuthStatus.LOADING -> {

                        }
                        AuthResource.AuthStatus.AUTHENTICATED -> {

                            Log.d(
                                LOG_TAG,
                                "onChanged: LOGIN SUCCESS: " + authResource.data.name
                            )
                        }
                        AuthResource.AuthStatus.ERROR -> {
                        }
                        AuthResource.AuthStatus.NOT_AUTHENTICATED -> {
                            navigateToLoginActivity()
                        }

                    }
                }

            }

        })
    }

    fun navigateToLoginActivity() {
        val intent: Intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}