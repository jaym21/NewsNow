package dev.jaym21.newsnow.data.remote.models.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.jaym21.newsnow.data.remote.models.entities.Article

@JsonClass(generateAdapter = true)
data class NewsResponse(
    @Json(name = "status")
    val status: String?,
    @Json(name = "totalResults")
    val totalResults: Int?,
    @Json(name = "articles")
    val articles: List<Article>?
)