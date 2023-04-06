package com.sup.diary

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sup.diary.databinding.ActivityInitialLoginBinding

class InitialLogin : AppCompatActivity() {
    lateinit var binding: ActivityInitialLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitialLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginButton.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            val sharedPreference =  getSharedPreferences("login_data_preference", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putString("username", username)
            editor.putString("password", password)
            editor.apply()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}