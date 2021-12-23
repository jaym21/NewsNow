package dev.jaym21.newsnow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.jaym21.newsnow.R

class CategoryRVAdapter(private val categories: MutableList<String>): RecyclerView.Adapter<CategoryRVAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.tvCategoryItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_category_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        holder.categoryName.text = categories[position]

    }

    override fun getItemCount(): Int {
        return categories.size
    }
}