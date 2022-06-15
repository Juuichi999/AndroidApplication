package com.example.myapplicationlvl1.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.myapplicationlvl1.R
import com.example.myapplicationlvl1.data.storage.CacheDataSource
import com.example.myapplicationlvl1.databinding.ActivityRegisterBinding
import com.example.myapplicationlvl1.utils.Constants
import com.example.myapplicationlvl1.utils.ParsingEmail
import com.example.myapplicationlvl1.utils.Validator
import com.example.myapplicationlvl1.utils.extensions.capitalizeWords


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var dataSource: CacheDataSource
    private lateinit var textWatcherEmail: TextWatcher
    private lateinit var textWatcherPassword: TextWatcher
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataSource = CacheDataSource(this)
        binding = ActivityRegisterBinding.inflate(layoutInflater).also { setContentView(it.root) }
        autoLogin()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            textWatcherEmail = textEditTextEmail.doAfterTextChanged {
                textInputLayoutEmail.isErrorEnabled = false
            }

            textWatcherPassword = textEditTextPassword.doAfterTextChanged {
                textInputLayoutPassword.isErrorEnabled = false
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.textEditTextEmail.removeTextChangedListener(textWatcherEmail)
        binding.textEditTextPassword.removeTextChangedListener(textWatcherPassword)
    }

    private fun setListeners() {
        with(binding) {

            buttonRegister.setOnClickListener {

                val email = textEditTextEmail.text.toString()
                val password = textEditTextPassword.text.toString()

                val emailValid = Validator.isEmailValid(email)
                val passwordValid = Validator.isPasswordValid(password)

                if (!emailValid) {
                    textInputLayoutEmail.error = getString(R.string.incorrect_email)
                }
                if (!passwordValid) {
                    textInputLayoutPassword.error = getString(R.string.incorrect_password)
                }

                if (emailValid && passwordValid) {
                    if (checkBoxRegistration.isChecked) {
                        with(dataSource) {
                            saveString(Constants.LOGIN_KEY, email)
                            saveString(Constants.PASSWORD_KEY, password)
                        }
                    }
                    goToProfile(email)
                }
            }
        }
    }

    private fun autoLogin() {
        val email = dataSource.getString(Constants.LOGIN_KEY, "")
        val password = dataSource.getString(Constants.PASSWORD_KEY, "")
        if (email != "" && password != "") {
            goToProfile(email.toString())
        }
    }

    private fun goToProfile(email: String) {
        val intent = Intent(this@RegisterActivity, ProfileActivity::class.java)
        val textEntered = ParsingEmail.parseEmailToFullName(email).capitalizeWords()
        intent.putExtra(Constants.LOGIN_KEY, textEntered)
        startActivity(intent)
        finish()
    }

}



