package dev.jaym21.newsnow.adapter

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.jaym21.newsnow.R

class CategoryRVAdapter(private val categories: List<String>, private val listener: ICategoryRVAdapter): RecyclerView.Adapter<CategoryRVAdapter.CategoryViewHolder>() {

    private var selectedPosition = 0

    inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.tvCategoryItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_category_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.categoryName.text = categories[position]

        holder.categoryName.setOnClickListener {

            listener.onCategoryClicked(categories[position])

            if (selectedPosition == position) {
                notifyItemChanged(position)
            }

            selectedPosition = position
            notifyDataSetChanged()
        }

        when(holder.itemView.context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                if (selectedPosition == position) {
                    holder.categoryName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                    holder.categoryName.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_item_selected_bg)
                } else {
                    holder.categoryName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white_alpha_60))
                    holder.categoryName.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_item_unselected_dark_bg)
                }
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                if (selectedPosition == position) {
                    holder.categoryName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                    holder.categoryName.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_item_selected_bg)
                } else {
                    holder.categoryName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black_alpha_70))
                    holder.categoryName.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_item_unselected_bg)
                }
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                if (selectedPosition == position) {
                    holder.categoryName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                    holder.categoryName.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_item_selected_bg)
                } else {
                    holder.categoryName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black_alpha_70))
                    holder.categoryName.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.category_item_unselected_bg)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}

interface ICategoryRVAdapter{
    fun onCategoryClicked(category: String)
}