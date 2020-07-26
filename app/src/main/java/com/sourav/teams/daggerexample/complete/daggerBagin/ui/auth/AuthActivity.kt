package com.sourav.teams.daggerexample.complete.daggerBagin.ui.auth

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.sourav.teams.daggerexample.complete.daggerBagin.R
import com.sourav.teams.daggerexample.complete.daggerBagin.network.model.User
import com.sourav.teams.daggerexample.complete.daggerBagin.ui.main.MainActivity
import com.sourav.teams.daggerexample.complete.daggerBagin.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class AuthActivity : DaggerAppCompatActivity(), View.OnClickListener {
    private var viewModel: AuthViewModel? = null

    private val LOG_TAG: String = AuthActivity::class.simpleName!!

    @Inject
    @JvmField
    var providerFactory: ViewModelProviderFactory? = null

    @Inject
    @JvmField
    var logo: Drawable? = null

    @Inject
    @JvmField
    var requestManager: RequestManager? = null

    private lateinit var progressBar: ProgressBar

    private lateinit var etUserId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        viewModel = providerFactory?.let {
            ViewModelProvider(this,
                it
            ).get(AuthViewModel::class.java)
        }
        setLogo()

        etUserId = findViewById(R.id.user_id_input)
        progressBar = findViewById(R.id.progress_bar)
        findViewById<View>(R.id.login_button).setOnClickListener(this)
        subscribeObserver()
    }

    private fun setLogo() {
        requestManager
            ?.load(logo)
            ?.into(findViewById<View>(R.id.login_logo) as ImageView)
    }

    companion object {
        private const val TAG = "AuthActivity"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_button -> attemptLogin()
        }
    }

    private fun attemptLogin() {
        if (TextUtils.isEmpty(etUserId.text.toString())) {
            return
        }
        viewModel?.getUserById((etUserId.text.toString()).toInt())
    }

    private fun subscribeObserver() {
        var observe = viewModel?.observeAuthState()
            ?.observe(this, object : Observer<AuthResource<User>> {

                override fun onChanged(authResource: AuthResource<User>?) {
                    if (authResource != null) {
                        when (authResource.status) {
                            AuthResource.AuthStatus.LOADING -> {
                                showProgressBar(true)
                            }
                            AuthResource.AuthStatus.AUTHENTICATED -> {
                                showProgressBar(false)
                                Log.d(
                                    LOG_TAG,
                                    "onChanged: LOGIN SUCCESS: " + authResource.data.name
                                )
                                onLoginSuccess()
                            }
                            AuthResource.AuthStatus.ERROR -> {
                                showProgressBar(false)
                                Toast.makeText(
                                    this@AuthActivity,
                                    authResource.message + "\n did you enter number between 1 to 10?",
                                    LENGTH_LONG
                                ).show()
                            }
                            AuthResource.AuthStatus.NOT_AUTHENTICATED -> showProgressBar(false)

                        }
                    }
                }
            })
    }

    fun onLoginSuccess() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showProgressBar(isVisible: Boolean) {
        if (isVisible) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
