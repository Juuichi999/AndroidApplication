package com.example.myapplicationlvl1.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class CacheDataSource(private val context: Context) : DataSource {
    //    private val sharedPreferences = context.getSharedPreferences(, MODE_PRIVATE)
//    override fun saveString(key: String, value: String) {
//        sharedPreferences.edit().putString(key, value).apply()
//    }
//    override fun getString(key: String, default: String): String? {
//        return sharedPreferences.getString(key, default)
//    }
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "UserPersonalInfo")

    override suspend fun getString(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[dataStoreKey]
    }

//    override suspend fun saveString(key: String, value: String) {
//        val dataStoreKey = stringPreferencesKey(key)
//        context.dataStore.edit { preferences ->
//            preferences[dataStoreKey] = value
//        }
//    }

    override suspend fun saveString(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }
}