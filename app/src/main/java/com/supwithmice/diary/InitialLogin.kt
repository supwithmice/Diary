package com.supwithmice.diary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.supwithmice.diary.core.SettingsModule.diaryPassword
import com.supwithmice.diary.core.SettingsModule.diaryUsername
import com.supwithmice.diary.databinding.ActivityInitialLoginBinding

class InitialLogin : AppCompatActivity() {
    lateinit var binding: ActivityInitialLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitialLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            if (binding.editTextUsername.text.toString() == "") {
                binding.usernameInputLayout.error = "Username cannot be empty"
            }

            if (binding.editTextPassword.text.toString() == "") {
                binding.passwordInputLayout.error = "Password cannot be empty"
            }

            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                diaryUsername = username
                diaryPassword = password
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}
