package dev.jaym21.newsapi.client

import dev.jaym21.newsapi.service.NewsAPI
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val API_KEY = "06327b2038d043118f222d4abf97fe9b"

object NewsAPIClient {

    private val BASE_URL = "https://newsapi.org/v2/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val api by lazy {
        retrofit.create(NewsAPI::class.java)
    }
}