package com.example.lab2.presentation.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab2.databinding.PostItemLayoutBinding
import com.example.lab2.domain.entities.News
import com.example.lab2.presentation.post.PostDescriptionActivity

class PostsAdapter(private var items: List<News>? = null) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun getItemCount(): Int {
        return items?.count() ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (items != null)
            holder.bind(items!![position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding = PostItemLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(dataBinding)
    }

    fun setValue(items: List<News>?) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var dataBinding: PostItemLayoutBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(item: News) {
            dataBinding.subtitle.text = item.subtitle
            dataBinding.title.text = item.title
            dataBinding.card.setOnClickListener {
                PostDescriptionActivity.startActivity(
                    context,
                    item.id
                )
            }
            Glide.with(context).load(item.imgUrl).centerCrop().into(dataBinding.image)
        }
    }
}