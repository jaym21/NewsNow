package dev.jaym21.newsapi.service

import dev.jaym21.newsapi.client.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("country")
        countryCode: String = "in",
        @Query("category")
        category: String,
        @Query("page")
        page: Int = 1,
    )
}