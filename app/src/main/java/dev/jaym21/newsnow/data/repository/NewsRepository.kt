package dev.jaym21.newsnow.data.repository

import androidx.room.withTransaction
import dev.jaym21.newsnow.data.local.NewsDatabase
import dev.jaym21.newsnow.data.networkBoundResource
import dev.jaym21.newsnow.data.remote.service.NewsAPI
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: NewsAPI, private val database: NewsDatabase) {

    //getting dao from database
    private val newsDAO = database.getNewsDAO()

    //function to get news from api or room database decided with the help of networkBoundResource function
    fun getNews(category: String) = networkBoundResource(
        query = {
            newsDAO.getAllArticles(category)
        },
        fetch = {
            api.getTopHeadlines(category)
        },
        saveFetchedResults = { articles ->
            if (!articles.articles.isNullOrEmpty()) {
                //adding the category which is fetched to keep a category for database to recognize while getting articles category specific
                articles.articles.forEach { article ->
                    article.category = category
                }
                database.withTransaction {
                    //first clearing the database of old articles
                    newsDAO.deleteAllArticles()
                    //inserting the new articles
                    newsDAO.addArticles(articles.articles)
                }
            }
        }
    )
}