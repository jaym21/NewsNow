package dev.jaym21.newsnow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.jaym21.newsnow.adapter.CategoryRVAdapter
import dev.jaym21.newsnow.adapter.ICategoryRVAdapter
import dev.jaym21.newsnow.adapter.NewsRVAdapter
import dev.jaym21.newsnow.databinding.ActivityMainBinding
import dev.jaym21.newsnow.utils.Constants
import dev.jaym21.newsnow.utils.DataStoreManager
import dev.jaym21.newsnow.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ICategoryRVAdapter {

    private var binding: ActivityMainBinding? = null
    private val newsAdapter = NewsRVAdapter()
    private val viewModel: NewsViewModel by viewModels()
    private val categories = listOf("General", "Business", "Entertainment", "Sports", "Health", "Science", "Technology")
    private var currentCategory = "General"
    private var currentPage = 1
    private var itemsDisplayed = 0
    private var totalArticles: Int? = null
    private var isScrolling: Boolean = false
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpCategoriesRecyclerView()

        setUpArticlesRecyclerView()

        viewModel.getNews(this, "General", 1, true)

        //observing news response using live data in news viewModel
        viewModel.news.observe(this, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    binding?.progressBar?.visibility = View.GONE
                    newsAdapter.submitList(response.data)
                    isLoading = false
                    //getting total articles in response from dataStore
                    DataStoreManager(this).totalArticles.asLiveData().observe(this) {
                        totalArticles = it
                    }
                    if (totalArticles != null) {
                        //setting boolean isLastPage according to the itemsDisplayed
                        isLastPage = itemsDisplayed + Constants.PAGE_SIZE_QUERY >= totalArticles!!
                    }

                    //increasing no of items displayed by 20
                    itemsDisplayed += Constants.PAGE_SIZE_QUERY
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

    //implementing pagination
    private val paginationScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            //checking if the user is scrolling
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            //getting the linear layout manager from recycler view
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager

            //now getting the first item position which is visible on page
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            //getting the total visible items count on page
            val visibleItemCount = layoutManager.childCount

            //getting the total items count in recycler view
            val totalItemsCount = layoutManager.itemCount

            //checking if user is not at the last page and is not loading news items
            val isNotAtLastPageAndNotLoading = !isLastPage && !isLoading

            //checking if user is at the last page of the response
            val isAtLastPage = firstVisibleItemPosition + visibleItemCount >= totalItemsCount

            //checking if user has scrolled from the first page
            val notAtBeginning = firstVisibleItemPosition >= 0

            //checking if the no.of items in 1 query response are all loaded in recycler view
            val isTotalMoreThanVisible = totalItemsCount >= Constants.PAGE_SIZE_QUERY

            //creating a boolean to know if to paginate or not
            val shouldPaginate = isNotAtLastPageAndNotLoading && isAtLastPage && isTotalMoreThanVisible && notAtBeginning && isScrolling
            if (shouldPaginate) {
                currentPage += 1
                viewModel.getNews(this@MainActivity, currentCategory, currentPage, false)
                isScrolling = false
            }
        }
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
            addOnScrollListener(paginationScrollListener)
        }
    }

    override fun onCategoryClicked(category: String) {
        currentCategory = category
        itemsDisplayed = 0
        currentPage = 1
        viewModel.getNews(this, category, 1, true)
    }
}