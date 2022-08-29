package com.example.myapplicationlvl1.utils

object Validator {

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        val regexPassword = Constants.REGEX_PASSWORD.toRegex()
        return regexPassword.matches(password)
    }

}
