package dev.jaym21.newsnow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.jaym21.newsnow.adapter.CategoryRVAdapter
import dev.jaym21.newsnow.adapter.ICategoryRVAdapter
import dev.jaym21.newsnow.adapter.NewsRVAdapter
import dev.jaym21.newsnow.databinding.ActivityMainBinding
import dev.jaym21.newsnow.utils.Resource

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ICategoryRVAdapter {

    private var binding: ActivityMainBinding? = null
    private val newsAdapter = NewsRVAdapter()
    private val viewModel: NewsViewModel by viewModels()
    private val categories = listOf("General", "Business", "Entertainment", "Sports", "Health", "Science", "Technology")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpCategoriesRecyclerView()

        setUpArticlesRecyclerView()

        viewModel.getNews(this, "General")

        //observing news response using live data in news viewModel
        viewModel.news.observe(this, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    binding?.progressBar?.visibility = View.GONE
                    newsAdapter.submitList(response.data?.articles)
                }
                is Resource.Error -> {
                    binding?.progressBar?.visibility = View.GONE
                    Snackbar.make(binding?.root!!, "${response.error}", Snackbar.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setUpCategoriesRecyclerView() {
        val categoryAdapter = CategoryRVAdapter(categories, this)
        binding?.rvCategory?.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpArticlesRecyclerView() {
        binding?.rvNews?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onCategoryClicked(category: String) {
        viewModel.getNews(this, category)
    }
}