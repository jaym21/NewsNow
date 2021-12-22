package dev.jaym21.newsnow.data.remote.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Source(
    @Json(name = "id")
    val id: String?,
    @Json(name = "name")
    val name: String?
)