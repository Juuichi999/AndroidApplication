package com.example.myapplicationlvl1.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.myapplicationlvl1.ProfileActivity
import com.example.myapplicationlvl1.R
import com.example.myapplicationlvl1.data.storage.CacheDataSource
import com.example.myapplicationlvl1.databinding.ActivityRegisterBinding
import com.example.myapplicationlvl1.utils.Constants
import com.example.myapplicationlvl1.utils.ParsingEmail
import com.example.myapplicationlvl1.utils.Validator


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
            textEditTextEmail.doOnTextChanged { _, _, _, _ ->
                textInputLayoutEmail.isErrorEnabled = false
            }
            textEditTextPassword.doOnTextChanged { _, _, _, _ ->
                textInputLayoutPassword.isErrorEnabled = false
            }
            buttonRegister.setOnClickListener {

                val email = textEditTextEmail.text.toString()
                val password = textEditTextPassword.text.toString()

                val emailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                val passwordValid = Validator.isPasswordValid(password)

                textInputLayoutEmail.error = if (!emailValid) {
                    getString(R.string.incorrect_email)
                } else ""
                textInputLayoutPassword.error = if (!passwordValid) {
                    getString(R.string.incorrect_password)
                } else ""

                if (emailValid && passwordValid) {
                    if (checkBoxRegistration.isEnabled) {
                        with(dataSource) {
                            saveString(Constants.LOGIN_KEY, email)
                            saveString(Constants.PASSWORD_KEY, password)
                        }
                    }
                    goToProfile()
                }
            }
        }
    }

    private fun autoLogin() {
        val email = dataSource.getString(Constants.LOGIN_KEY, "")
        val password = dataSource.getString(Constants.PASSWORD_KEY, "")
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
        val textEntered = ParsingEmail.parseEmailToFullName(binding.textEditTextEmail.text.toString())
        intent.putExtra(Intent.EXTRA_TEXT, textEntered)
        startActivity(intent)
        finish()
    }

}



