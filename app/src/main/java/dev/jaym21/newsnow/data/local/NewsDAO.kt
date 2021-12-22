package dev.jaym21.newsnow.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.jaym21.newsnow.data.remote.models.entities.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticles(article: Article)

    @Query("DELETE FROM article_table")
    suspend fun deleteAllArticles()

    @Query("SELECT * FROM article_table category WHERE :category")
    fun getAllArticles(category: String): Flow<List<Article>>
}