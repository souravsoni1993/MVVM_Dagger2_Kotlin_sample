package com.sourav.teams.daggerexample.complete.daggerBagin.ui.main.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sourav.teams.daggerexample.complete.daggerBagin.R
import com.sourav.teams.daggerexample.complete.daggerBagin.databinding.FragmentProfileBinding
import com.sourav.teams.daggerexample.complete.daggerBagin.network.model.User
import com.sourav.teams.daggerexample.complete.daggerBagin.ui.auth.AuthResource
import com.sourav.teams.daggerexample.complete.daggerBagin.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProfileFragment : DaggerFragment() {

    private val LOG_TAG: String = ProfileFragment::class.simpleName!!
    private lateinit var profileViewModel: ProfileViewModel

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var userEmail: TextView
    private lateinit var userName: TextView
    private lateinit var userWebsite: TextView
    private var fragmentProfileBinding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "profile fragment was created")
        super.onViewCreated(view, savedInstanceState)

        fragmentProfileBinding = FragmentProfileBinding.bind(view)

        profileViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(ProfileViewModel::class.java)

        profileViewModel.observeUserDetails().removeObservers(viewLifecycleOwner)
        profileViewModel.observeUserDetails()
            .observe(viewLifecycleOwner, object : Observer<AuthResource<User>> {
                override fun onChanged(authResource: AuthResource<User>?) {
                    if (authResource != null) {

                        when (authResource.status) {
                            AuthResource.AuthStatus.AUTHENTICATED -> setUserDetails(authResource.data)
                            AuthResource.AuthStatus.AUTHENTICATED -> setErrorDetails(authResource.message)
                        }
                    }
                }

            })
    }

    private fun setErrorDetails(error: String?) {
        val user = User(null, null, error, null,error,null,null,error)
        fragmentProfileBinding?.user=user
        fragmentProfileBinding?.executePendingBindings()
    }

    private fun setUserDetails(user: User) {
        fragmentProfileBinding?.user = user
        fragmentProfileBinding?.executePendingBindings()
    }

    override fun onDestroy() {
        fragmentProfileBinding = null
        super.onDestroy()
    }
}