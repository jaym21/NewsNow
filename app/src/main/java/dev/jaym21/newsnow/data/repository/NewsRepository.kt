package dev.jaym21.newsnow.data.repository

import android.content.Context
import android.util.Log
import dev.jaym21.newsnow.data.local.NewsDatabase
import dev.jaym21.newsnow.data.remote.models.entities.Article
import dev.jaym21.newsnow.data.remote.models.responses.NewsResponse
import dev.jaym21.newsnow.data.remote.service.NewsAPI
import dev.jaym21.newsnow.utils.NetworkUtils
import dev.jaym21.newsnow.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: NewsAPI, private val database: NewsDatabase) {

    //getting dao from database
    private val newsDAO = database.getNewsDAO()

    fun getNews(context: Context, category: String, pageNo: Int): Flow<Resource<NewsResponse>> {
        return flow {
            if (NetworkUtils.getNetworkStatus(context)) {
                val response = api.getTopHeadlines(category)
                if (response.isSuccessful) {
                    val body = response.body()

                    if (body?.articles != null) {

                        newsDAO.deleteArticlesFor(category)
                        body.articles.forEach {
                            it.category = category
                            newsDAO.addArticle(it)
                        }

                        emit(Resource.Success(body))
                    } else {
                        emit(Resource.Error("No response from server"))
                    }
                } else {
                    emit(Resource.Error("Server Error"))
                }
            } else {
                val articlesFlow = newsDAO.getAllArticles(category)
                articlesFlow.collect {
                    if (it.isNullOrEmpty()) {
                        emit(Resource.Error("No internet connection"))
                    } else {
                        emit(Resource.Success(NewsResponse("ok", it.size, it)))
                    }
                }
            }
        }
    }
}