package dev.jaym21.newsnow.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jaym21.newsnow.data.remote.models.entities.Article
import dev.jaym21.newsnow.data.repository.NewsRepository
import dev.jaym21.newsnow.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    private val _news: MutableLiveData<Resource<List<Article>>> = MutableLiveData()
    val news: LiveData<Resource<List<Article>>> = _news

    fun getNews(category: String) = viewModelScope.launch(Dispatchers.IO) {
        val news = repository.getNews(category)

        news.collect {
            _news.postValue(it)
        }
    }

}