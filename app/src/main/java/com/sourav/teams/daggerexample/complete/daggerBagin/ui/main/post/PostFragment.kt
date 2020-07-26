package com.sourav.teams.daggerexample.complete.daggerBagin.ui.main.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sourav.teams.daggerexample.complete.daggerBagin.R
import com.sourav.teams.daggerexample.complete.daggerBagin.network.model.PostItem
import com.sourav.teams.daggerexample.complete.daggerBagin.util.VerticalSpaceItemDecoration
import com.sourav.teams.daggerexample.complete.daggerBagin.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PostFragment : DaggerFragment() {

    private val LOG_TAG: String = PostViewModel::class.simpleName!!

    private lateinit var recyclerView: RecyclerView

    private lateinit var postViewModel: PostViewModel

    private lateinit var progressBar: ProgressBar

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_post)
        postViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(PostViewModel::class.java)

        initRecyclerView()
        showProgress(true)
        subscribeObserver()
    }

    private fun subscribeObserver() {
        postViewModel.observeUserPost().removeObservers(viewLifecycleOwner)
        postViewModel.observeUserPost()
            .observe(viewLifecycleOwner, object : Observer<Resource<List<PostItem>>> {
                override fun onChanged(resource: Resource<List<PostItem>>?) {
                    if (resource != null) {
                        when (resource.status) {
                            Resource.Status.LOADING -> {
                                Log.d(LOG_TAG, "fetching date")
                                showProgress(true)
                            }

                            Resource.Status.SUCCESS -> {
                                showProgress(false)
                                postAdapter.setPosts(resource.data)
                            }

                            Resource.Status.ERROR -> {
                                showProgress(false)
                                Log.d(LOG_TAG, "error while getting data")

                            }

                        }
                    }
                }

            })

    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val verticalSpaceItemDecoration: VerticalSpaceItemDecoration =
            VerticalSpaceItemDecoration(15)
        recyclerView.addItemDecoration(verticalSpaceItemDecoration)
        recyclerView.adapter = postAdapter
    }

    private fun showProgress(isVisible: Boolean) {

        if (isVisible) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }

    }
}