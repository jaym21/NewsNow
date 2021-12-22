package dev.jaym21.newsnow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jaym21.newsnow.R
import dev.jaym21.newsnow.adapter.NewsRVAdapter
import dev.jaym21.newsnow.databinding.ActivityMainBinding
import dev.jaym21.newsnow.utils.Resource

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val newsAdapter = NewsRVAdapter()
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpRecyclerView()

        //getting news
        viewModel.getNews("business")


        //observing news response using live data in news viewModel
        viewModel.news.observe(this, Observer { response ->
            when(response) {
                is Resource.Success -> {

                }
            }

        })
    }

    private fun setUpRecyclerView() {
        binding?.rvNews?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }
}