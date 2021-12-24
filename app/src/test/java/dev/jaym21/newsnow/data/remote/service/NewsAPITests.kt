package dev.jaym21.newsnow.data.remote.service


import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test
import javax.inject.Inject

class NewsAPITests @Inject constructor(private val api: NewsAPI) {

    @Test
    fun `get top headlines`() = runBlocking {
        val response = api.getTopHeadlines("business")
        assertNotNull(response)
    }
}