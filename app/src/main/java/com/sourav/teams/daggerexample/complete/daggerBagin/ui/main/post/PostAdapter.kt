package com.sourav.teams.daggerexample.complete.daggerBagin.ui.main.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sourav.teams.daggerexample.complete.daggerBagin.databinding.LayoutPostItemLayoutBinding
import com.sourav.teams.daggerexample.complete.daggerBagin.network.model.PostItem

class PostAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var posts: List<PostItem> = mutableListOf();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutPostItemLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).bind(posts.get(position))
    }

    class PostViewHolder(val binding: LayoutPostItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(postItem: PostItem) {
            binding.post = postItem
            binding.executePendingBindings()
        }

    }

    fun setPosts(posts: List<PostItem>) {
        this.posts = posts
        notifyDataSetChanged()
    }

}