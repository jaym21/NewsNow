package dev.jaym21.newsapi.service

import dev.jaym21.newsapi.client.API_KEY
import dev.jaym21.newsapi.models.responses.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("category")
        category: String,
        @Query("page")
        page: Int = 1,
        @Query("country")
        countryCode: String = "in",
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}