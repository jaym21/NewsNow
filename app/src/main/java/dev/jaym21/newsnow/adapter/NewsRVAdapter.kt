package dev.jaym21.newsnow.adapter

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dev.jaym21.newsnow.R
import dev.jaym21.newsnow.data.remote.models.entities.Article

class NewsRVAdapter(private val listener: INewsRVAdapter): ListAdapter<Article, NewsRVAdapter.NewsViewHolder>(NewsDiffUtil()) {

    class NewsDiffUtil: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val description: TextView = itemView.findViewById(R.id.tvDes)
        val image: ImageView = itemView.findViewById(R.id.newsImage)
        val name: TextView = itemView.findViewById(R.id.tvName)
        val root: LinearLayout = itemView.findViewById(R.id.llNewsArticleRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_news_article_layout, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        if (currentItem.source?.name != null)
            holder.name.text = currentItem.source.name

        Glide.with(holder.itemView.context).load(currentItem.urlToImage).transform(CenterCrop(), RoundedCorners(30)).into(holder.image)

        when(holder.itemView.context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                holder.title.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                holder.description.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white_alpha_70))
                holder.name.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white_alpha_85))
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                holder.title.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
                holder.description.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black_alpha_70))
                holder.name.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black_alpha_70))
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                holder.title.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
                holder.description.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black_alpha_70))
                holder.name.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black_alpha_70))
            }
        }

        holder.root.setOnClickListener {
            listener.onArticleClicked(currentItem)
        }
    }
}

interface INewsRVAdapter {
    fun onArticleClicked(article: Article)
}