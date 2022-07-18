package com.example.myapplicationlvl1.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationlvl1.databinding.ActivityProfileBinding
import com.example.myapplicationlvl1.databinding.ActivityProfileBinding.inflate
import com.example.myapplicationlvl1.utils.Constants


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater).also { setContentView(it.root) }
        val name = intent.getStringExtra(Constants.LOGIN_KEY)
        binding.fullNameTextView.text = name
    }
}