package com.example.myapplicationlvl1.data.storage

interface DataSource {

    //    fun saveString(key: String, value: String)

    suspend fun saveString(key: String, value: String)

    suspend fun getString(key: String): String?

}