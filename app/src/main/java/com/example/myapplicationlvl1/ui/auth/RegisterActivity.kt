package com.example.myapplicationlvl1.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.myapplicationlvl1.R
import com.example.myapplicationlvl1.data.storage.CacheDataSource
import com.example.myapplicationlvl1.data.storage.DataSource
import com.example.myapplicationlvl1.databinding.ActivityRegisterBinding
import com.example.myapplicationlvl1.ui.profile.ProfileActivity
import com.example.myapplicationlvl1.utils.Constants
import com.example.myapplicationlvl1.utils.ParsingEmail
import com.example.myapplicationlvl1.utils.Validator
import com.example.myapplicationlvl1.utils.extensions.capitalizeWords
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var textWatcherEmail: TextWatcher
    private val dataSource: DataSource by lazy { CacheDataSource(this) }
    private lateinit var textWatcherPassword: TextWatcher
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater).also { setContentView(it.root) }
        autoLogin()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            textWatcherEmail = emailTextEditText.doAfterTextChanged {
                emailTextInputLayout.isErrorEnabled = false
            }

            textWatcherPassword = passwordTextEditText.doAfterTextChanged {
                passwordTextInputLayout.isErrorEnabled = false
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.apply {
            emailTextEditText.removeTextChangedListener(textWatcherEmail)
            passwordTextEditText.removeTextChangedListener(textWatcherPassword)
        }
    }

    private fun setListeners() {
        with(binding) {

            registerButton.setOnClickListener {

                val email = emailTextEditText.text.toString()
                val password = passwordTextEditText.text.toString()

                val emailValid = Validator.isEmailValid(email)
                val passwordValid = Validator.isPasswordValid(password)

                if (!emailValid) {
                    emailTextInputLayout.error = getString(R.string.incorrect_email)
                }
                if (!passwordValid) {
                    passwordTextInputLayout.error = getString(R.string.incorrect_password)
                }

                if (emailValid && passwordValid) {
                    if (rememberUserCheckBox.isChecked) {
                        lifecycleScope.launch {
                            with(dataSource) {
                                saveString(Constants.LOGIN_KEY, email)
                                saveString(Constants.PASSWORD_KEY, password)
                            }
                        }
                    }
                    goToProfile(email)
                }
            }
        }
    }

    private fun autoLogin() {
        runBlocking { //crutch
            dataSource.apply {
                val email = getString(Constants.LOGIN_KEY)
                val password = getString(Constants.PASSWORD_KEY)
                if (email != null && password != null) {
                    goToProfile(email.toString())
                }
            }
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



