package dev.jaym21.newsnow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.jaym21.newsnow.data.remote.models.entities.Article

@Database(entities = [Article::class], exportSchema = false, version = 1)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun getNewsDAO(): NewsDAO
}