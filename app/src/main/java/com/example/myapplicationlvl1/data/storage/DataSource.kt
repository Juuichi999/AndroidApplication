package com.example.myapplicationlvl1.data.storage

interface DataSource {

    suspend fun saveString(key: String, value: String)

    suspend fun getString(key: String): String?

}