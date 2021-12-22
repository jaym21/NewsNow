package dev.jaym21.newsnow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.jaym21.newsnow.R
import dev.jaym21.newsnow.data.remote.models.entities.Article

class NewsRVAdapter: ListAdapter<Article, NewsRVAdapter.NewsViewHolder>(NewsDiffUtil()) {

    class NewsDiffUtil: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.tvTitle)
        val newsImage: ImageView = itemView.findViewById(R.id.newsImage)
        val name: TextView = itemView.findViewById(R.id.tvName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_news_article_layout, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.titleView.text = currentItem.title
        if (currentItem.source?.name != null)
            holder.name.text = currentItem.source.name
    }
}