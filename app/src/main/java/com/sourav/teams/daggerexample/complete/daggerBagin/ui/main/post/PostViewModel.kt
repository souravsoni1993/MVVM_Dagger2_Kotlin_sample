package com.sourav.teams.daggerexample.complete.daggerBagin.ui.main.post

import android.util.Log
import androidx.lifecycle.*
import com.sourav.teams.daggerexample.complete.daggerBagin.SessionManager
import com.sourav.teams.daggerexample.complete.daggerBagin.network.main.MainApi
import com.sourav.teams.daggerexample.complete.daggerBagin.network.model.PostItem
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val mainApi: MainApi
) : ViewModel() {

    private val LOG_TAG: String = PostViewModel::class.simpleName!!

    private var posts: MediatorLiveData<Resource<List<PostItem>>>? = null

    private val emptyList:List<PostItem> = emptyList()

    fun observeUserPost(): LiveData<Resource<List<PostItem>>> {

        if (posts == null) {
            posts = MediatorLiveData()
            posts!!.value = Resource.loading(emptyList)

            val source: LiveData<Resource<List<PostItem>>> =
                LiveDataReactiveStreams.fromPublisher(

                    mainApi.getUserSpecificPost(sessionManager.getAuthUser().value?.data?.id!!)

                        .onErrorReturn(object : Function<Throwable, List<PostItem>> {
                            override fun apply(t: Throwable): List<PostItem> {
                                Log.e(LOG_TAG, "apply: ", t)
                                val postItem: PostItem = PostItem(null, -1, null, null)
                                val postList = mutableListOf<PostItem>();
                                postList.add(postItem)
                                return postList
                            }
                        })

                        .map(object : Function<List<PostItem>, Resource<List<PostItem>>> {
                            override fun apply(postItem: List<PostItem>): Resource<List<PostItem>> {
                                if (postItem.size > 0) {
                                    if (postItem.get(0).id == -1) {
                                        return Resource.error(
                                            "something went wrong",
                                             emptyList
                                        )
                                    }
                                }
                                return Resource.success(postItem)

                            }


                        })
                        .subscribeOn(Schedulers.io())
                )


            posts!!.addSource(source, object : Observer<Resource<List<PostItem>>> {
                override fun onChanged(postList: Resource<List<PostItem>>?) {
                    posts!!.value = postList
                    posts!!.removeSource(source)
                }

            })

        }
        return posts as MediatorLiveData<Resource<List<PostItem>>>

    }


}