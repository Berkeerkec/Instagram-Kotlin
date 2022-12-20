package com.berkesoft.instakotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.berkesoft.instakotlin.databinding.RecyclerRowBinding
import com.berkesoft.instakotlin.model.Post
import com.squareup.picasso.Picasso

class PostAdapter (val postList : ArrayList<Post>): RecyclerView.Adapter<PostAdapter.PostHolder>() {

    class PostHolder (val binding : RecyclerRowBinding): ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.recyclerEmailText.text = postList.get(position).email
        holder.binding.recyclerCommentText.text = postList.get(position).comment
        Picasso.get().load(postList.get(position).url).into(holder.binding.recyclerImage)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}