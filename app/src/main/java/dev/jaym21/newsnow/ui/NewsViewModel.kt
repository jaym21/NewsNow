package dev.jaym21.newsnow.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jaym21.newsnow.data.remote.models.responses.NewsResponse
import dev.jaym21.newsnow.data.repository.NewsRepository
import dev.jaym21.newsnow.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    private val _news: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val news: LiveData<Resource<NewsResponse>> = _news

    fun getNews(context: Context, category: String) = viewModelScope.launch {
        _news.postValue(Resource.Loading())
        repository.getNews(context, category)
            .onStart {
                Resource.Loading<NewsResponse>()
                Log.d("TAGYOYO", "getNews: onStart")
            }
            .flowOn(Dispatchers.IO)
            .catch {
                _news.postValue(Resource.Error("No internet connection"))
                Log.d("TAGYOYO", "getNews: catch")
            }
            .collect {
                Log.d("TAGYOYO", "collect ${it.data}")
                if (it.data != null)
                    _news.postValue(Resource.Success(it.data))
            }
    }
}
