package dev.jaym21.newsnow.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jaym21.newsnow.data.remote.models.entities.Article
import dev.jaym21.newsnow.data.remote.models.responses.NewsResponse
import dev.jaym21.newsnow.data.repository.NewsRepository
import dev.jaym21.newsnow.utils.DataStoreManager
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

    private val _news: MutableLiveData<Resource<List<Article>>> = MutableLiveData()
    val news: LiveData<Resource<List<Article>>> = _news
    private var allNewsDisplayed = mutableListOf<Article>()

    fun getNews(context: Context, category: String, pageNo: Int, isNew: Boolean) = viewModelScope.launch {
        _news.postValue(Resource.Loading())

        //checking if it is a new call for news or a paginated call
        if (isNew) {
            //if call is new then clearing the previous paginated news
            allNewsDisplayed.clear()
        }
        repository.getNews(context, category, pageNo)
            .onStart {
                Resource.Loading<NewsResponse>()
            }
            .flowOn(Dispatchers.IO)
            .catch {
                _news.postValue(Resource.Error("No internet connection"))
            }
            .collect {
//                Log.d("TAGYOYO", "collect ${it.data} ")
                if (it.data?.articles != null) {

                    //storing the response received from api for pagination
                    allNewsDisplayed.addAll(it.data.articles)
                    _news.postValue(Resource.Success(allNewsDisplayed.toList()))
                } else {
                    _news.postValue(Resource.Error("No internet connection"))
                }
            }
    }
}
