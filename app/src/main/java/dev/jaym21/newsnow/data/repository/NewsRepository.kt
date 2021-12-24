package dev.jaym21.newsnow.data.repository

import android.content.Context
import android.util.Log
import dev.jaym21.newsnow.data.local.NewsDatabase
import dev.jaym21.newsnow.data.remote.models.entities.Article
import dev.jaym21.newsnow.data.remote.models.responses.NewsResponse
import dev.jaym21.newsnow.data.remote.service.NewsAPI
import dev.jaym21.newsnow.utils.NetworkUtils
import dev.jaym21.newsnow.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: NewsAPI, private val database: NewsDatabase) {

    //getting dao from database
    private val newsDAO = database.getNewsDAO()

    fun getNews(context: Context, category: String): Flow<Resource<NewsResponse>> {
        Log.d("TAGYOYO", "getNews: repo")
        return flow {
            if (NetworkUtils.getNetworkStatus(context)) {
                Log.d("TAGYOYO", "network true")
                val response = api.getTopHeadlines(category)
                if (response.isSuccessful) {
                    val body = response.body()

                    if (body?.articles != null) {

                        newsDAO.deleteAllArticles()
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
                Log.d("TAGYOYO", "network false")
                val articlesFlow = newsDAO.getAllArticles(category)
                var articles: List<Article>? = null
                articlesFlow.collect {
                    Log.d("TAGYOYO", "article flow collect $it")
                    if (it.isNullOrEmpty()) {
                        emit(Resource.Error("No internet connection"))
                    } else {
                        articles = it
                    }
                }
                Log.d("TAGYOYO", "articles $articles")
                if (articles != null)
                    emit(Resource.Success(NewsResponse("ok", articles?.size, articles)))
                else
                    emit(Resource.Error("No internet connection"))
            }
        }
    }
}