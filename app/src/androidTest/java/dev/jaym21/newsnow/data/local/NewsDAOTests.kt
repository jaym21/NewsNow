package dev.jaym21.newsnow.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.jaym21.newsnow.data.remote.models.entities.Article
import dev.jaym21.newsnow.data.remote.models.entities.Source
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsDAOTests {

    private lateinit var database: NewsDatabase

    @Before
    fun init() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsDatabase::class.java
        ).build()
    }



    @Test
    fun inserting_a_article_in_database () = runBlocking {
        val source = Source(null, "24/7 Wall St.")
        val article = Article(
            1,
            source,
            "247chrislange",
            "Tuesday Afternoon’s Analyst Upgrades and Downgrades: EVgo, Luminar Technologies, Tencent Music Entertainment, Tesla and More",
            "Tuesday afternoon's top analyst upgrades and downgrades included EVgo, Luminar Technologies, Tencent Music Entertainment and Tesla.",
            "https://247wallst.com/investing/2021/12/28/tuesday-afternoons-analyst-upgrades-and-downgrades-evgo-luminar-technologies-tencent-music-entertainment-tesla-and-more/",
            "https://247wallst.com/wp-content/uploads/2021/03/imageForEntry1-nmL.jpg",
            "2021-12-28T16:45:55Z",
            "Markets were somewhat mixed on Tuesday, with the Nasdaq giving back some of its gains from Monday. The S&amp;P 500 hit more fresh all-time highs on Tuesday morning but then pulled back slightly. The … [+2183 chars]",
            null
        )

        database.getNewsDAO().addArticle(article)

        val articles = database.getNewsDAO().getAllArticles().first()

        assertThat(articles, equalTo(article))
    }

    @After
    fun cleanup() {
        database.close()
    }
}