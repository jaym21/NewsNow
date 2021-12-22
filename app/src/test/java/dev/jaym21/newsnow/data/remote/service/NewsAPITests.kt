package dev.jaym21.newsnow.data.remote.service


import dev.jaym21.newsnow.data.remote.client.NewsAPIClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test

class NewsAPITests {

    val api = NewsAPIClient.api

    @Test
    fun `get top headlines`() = runBlocking {
        val response = api.getTopHeadlines("business")
        assertNotNull(response.body())
    }
}