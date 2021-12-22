package dev.jaym21.newsnow.data.remote.client


import dev.jaym21.newsnow.data.remote.service.NewsAPI
import dev.jaym21.newsnow.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NewsAPIClient {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val api by lazy {
        retrofit.create(NewsAPI::class.java)
    }
}