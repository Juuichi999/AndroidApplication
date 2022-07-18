package com.example.myapplicationlvl1.utils

object Validator {
    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        val regexPassword =
            """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([@#${'$'}%!\-_?&])?(?=\S+${'$'}).{8,24}"""
                .toRegex()
        return regexPassword.matches(password)
    }


}
