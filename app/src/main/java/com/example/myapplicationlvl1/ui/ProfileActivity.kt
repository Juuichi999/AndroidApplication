package com.example.myapplicationlvl1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationlvl1.databinding.ActivityProfileBinding
import com.example.myapplicationlvl1.databinding.ActivityProfileBinding.inflate


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater).also { setContentView(it.root) }
        val name = intent.extras
    }
    companion object{
        @JvmStatic val EXTRA_OPTIONS = "EXTRA_OPTIONS"

    }
}