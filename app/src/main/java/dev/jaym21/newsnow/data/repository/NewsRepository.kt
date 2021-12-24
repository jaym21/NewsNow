package dev.jaym21.newsnow.data.repository

import android.util.Log
import androidx.room.withTransaction
import dev.jaym21.newsnow.data.local.NewsDatabase
import dev.jaym21.newsnow.data.remote.models.entities.Article
import dev.jaym21.newsnow.data.remote.models.responses.NewsResponse
import dev.jaym21.newsnow.data.remote.service.NewsAPI
import dev.jaym21.newsnow.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: NewsAPI, private val database: NewsDatabase) {

    //getting dao from database
    private val newsDAO = database.getNewsDAO()

    //function to get news from api or room database decided with the help of networkBoundResource function
//    fun getNews(category: String) = networkBoundResource(
//        query = {
//            newsDAO.getAllArticles(category)
//        },
//        fetch = {
//            api.getTopHeadlines(category)
//        },
//        saveFetchedResults = { articles ->
//            Log.d("TAGYOYO", "Repository ${articles.body()?.articles}")
//            if (!articles.body()?.articles.isNullOrEmpty()) {
//                //adding the category which is fetched to keep a category for database to recognize while getting articles category specific
//                articles.body()?.articles?.forEach { article ->
//                    article.category = category
//                }
//                database.withTransaction {
//                    //first clearing the database of old articles
//                    newsDAO.deleteAllArticles()
//                    //inserting the new articles
//                    newsDAO.addArticles(articles.body()?.articles!!)
//                }
//            }
//        }
//    )

    fun getNews(category: String): Flow<Resource<NewsResponse>> {
        return flow {
            val response = api.getTopHeadlines(category)
            if (response.isSuccessful) {
                val body = response.body()
                emit(Resource.Success(body!!))
            } else {
                emit(Resource.Error("Error"))
            }
        }
    }
}