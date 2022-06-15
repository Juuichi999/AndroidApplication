package com.example.myapplicationlvl1.data.storage

interface DataSource {

    fun saveString(key: String, value: String)

    fun getString(key: String, default: String): String?

}