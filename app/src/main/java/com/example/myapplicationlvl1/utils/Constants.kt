package com.example.myapplicationlvl1.utils

object Constants {
    const val LOGIN_KEY = "LOGIN_KEY"
    const val PASSWORD_KEY = "PASSWORD_KEY"
    const val REGEX_PASSWORD =
        """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([@#${'$'}%!\-_?&])?(?=\S+${'$'}).{8,24}"""
}