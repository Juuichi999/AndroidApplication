package com.example.myapplicationlvl1

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationlvl1.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputEditText

const val LOGIN_KEY = "LOGIN_KEY"
const val PASSWORD_KEY = "PASSWORD_KEY"

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var dataSource: CacheDataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataSource = CacheDataSource(this)
        binding = ActivityRegisterBinding.inflate(layoutInflater).also { setContentView(it.root) }
        autoLogin()
        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            textEditTextEmail.listenChanges { textInputLayoutEmail.error = "" }
            textEditTextPassword.listenChanges { textInputLayoutPassword.error = "" }
            buttonRegister.setOnClickListener {

                val email = textEditTextEmail.text.toString()
                val password = textEditTextPassword.text.toString()
                val regexPassword =
                    """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([@#${'$'}%!\-_?&])?(?=\S+${'$'}).{8,24}"""
                        .toRegex()
                val emailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                val passwordValid = regexPassword.matches(password)
                textInputLayoutEmail.error = if (!emailValid) {
                    getString(R.string.incorrect_email)
                } else ""
                textInputLayoutPassword.error = if (!passwordValid) {
                    getString(R.string.incorrect_password)
                } else ""

                if (emailValid && passwordValid) {
                    if (checkBoxRegistration.isEnabled) {
                        with(dataSource) {
                            saveString(LOGIN_KEY, email)
                            saveString(PASSWORD_KEY, password)
                        }
                    }
                    goToProfile()
                }
            }
        }
    }

    private fun autoLogin() {
        val email = dataSource.getString(LOGIN_KEY, "")
        val password = dataSource.getString(PASSWORD_KEY, "")
        if (email != "" && password != "") {
            goToProfile()
        }
    }

//    private fun errorChecker(valid: Boolean, message: Int): String {
//        return if (!valid) {
//            getString(message)
//        } else ""
//    }

    private fun goToProfile() {
        val intent = Intent(this@RegisterActivity, ProfileActivity::class.java)
        val textEntered = parseEmailToFullName(binding.textEditTextEmail.text.toString())
        intent.putExtra(Intent.EXTRA_TEXT, textEntered)
        startActivity(intent)
        finish()
    }

    private fun TextInputEditText.listenChanges(block: (text: String) -> Unit) {
        addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(p0: Editable?) {
                block.invoke(p0.toString())
            }
        })
    }
}

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

private fun String.capitalizeWords() = split(" ")
    .joinToString(" ") { it -> it.lowercase().replaceFirstChar { it.uppercase() } }
