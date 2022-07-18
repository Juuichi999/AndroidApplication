package com.example.myapplicationlvl1.utils

import android.util.Patterns

object ParsingEmail {
    fun parseEmailToFullName(email: String): String {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            val emailWithout = email.substring(0, email.indexOf("@"))
            val array: List<String> = emailWithout.split(".")
            return if (array.size == 2) {
                "${array[0]} ${array[1]}"
            } else {
                emailWithout
            }
        }
        return email
    }
}
