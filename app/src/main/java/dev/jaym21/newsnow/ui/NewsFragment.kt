package dev.jaym21.newsnow.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.jaym21.newsnow.adapter.CategoryRVAdapter
import dev.jaym21.newsnow.adapter.ICategoryRVAdapter
import dev.jaym21.newsnow.adapter.NewsRVAdapter
import dev.jaym21.newsnow.databinding.FragmentNewsBinding
import dev.jaym21.newsnow.utils.Constants
import dev.jaym21.newsnow.utils.DataStoreManager
import dev.jaym21.newsnow.utils.Resource

@AndroidEntryPoint
class NewsFragment : Fragment(), ICategoryRVAdapter {

    private var binding: FragmentNewsBinding? = null
    private val newsAdapter = NewsRVAdapter()
    private lateinit var navController: NavController
    private lateinit var viewModel: NewsViewModel
    private val categories = listOf("General", "Business", "Entertainment", "Sports", "Health", "Science", "Technology")
    private var currentCategory = "General"
    private var currentPage = 1
    private var itemsDisplayed = 0
    private var totalArticles: Int? = null
    private var isScrolling: Boolean = false
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing navController
        navController = Navigation.findNavController(view)

        //initializing viewModel
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        setUpCategoriesRecyclerView()

        setUpArticlesRecyclerView()

        viewModel.getNews(requireContext(), "General", 1, true)

        //observing news response using live data in news viewModel
        viewModel.news.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    binding?.progressBar?.visibility = View.GONE
                    newsAdapter.submitList(response.data)
                    isLoading = false
                    //getting total articles in response from dataStore
                    DataStoreManager(requireContext()).totalArticles.asLiveData().observe(viewLifecycleOwner) {
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
                viewModel.getNews(requireContext(), currentCategory, currentPage, false)
                isScrolling = false
            }
        }
    }

    private fun setUpCategoriesRecyclerView() {
        val categoryAdapter = CategoryRVAdapter(categories, this)
        binding?.rvCategory?.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpArticlesRecyclerView() {
        binding?.rvNews?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addOnScrollListener(paginationScrollListener)
        }
    }

    override fun onCategoryClicked(category: String) {
        currentCategory = category
        itemsDisplayed = 0
        currentPage = 1
        viewModel.getNews(requireContext(), category, 1, true)
    }
}