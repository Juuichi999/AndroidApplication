package com.example.myapplicationlvl1.utils.extensions

fun String.capitalizeWords() = split(" ")
    .joinToString(" ") { it -> it.lowercase().replaceFirstChar { it.uppercase() } }
