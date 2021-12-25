package dev.jaym21.newsnow.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(val context: Context) {

    companion object {
        private val TOTAL_ARTICLES = intPreferencesKey("TOTAL_ARTICLES")
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATASTORE)
    }

    suspend fun saveTotalArticles(total: Int) {
        context.dataStore.edit {
            it[TOTAL_ARTICLES] = total
        }
    }

    val totalArticles: Flow<Int>
        get() = context.dataStore.data.map {
            it[TOTAL_ARTICLES] ?: 0
        }
}