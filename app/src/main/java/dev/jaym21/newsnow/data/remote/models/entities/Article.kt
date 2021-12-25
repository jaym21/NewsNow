package dev.jaym21.newsnow.data.remote.models.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity(tableName = "article_table")
@JsonClass(generateAdapter = true)
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @Json(name = "source")
    val source: Source?,
    @Json(name = "author")
    val author: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "urlToImage")
    val urlToImage: String?,
    @Json(name = "publishedAt")
    val publishedAt: String?,
    @Json(name = "content")
    val content: String?,
    var category: String?
): Serializable