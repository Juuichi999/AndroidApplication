package com.example.myapplicationlvl1.data.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE

class CacheDataSource(context: Context) : DataSource {
    private val sharedPreferences = context.getSharedPreferences("PERSON_INFO", MODE_PRIVATE)
    override fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
    override fun getString(key: String, default: String): String? {
        return sharedPreferences.getString(key, default)
    }
}