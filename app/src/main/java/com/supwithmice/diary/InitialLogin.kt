package com.supwithmice.diary

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.supwithmice.diary.databinding.ActivityInitialLoginBinding

class InitialLogin : AppCompatActivity() {
    lateinit var binding: ActivityInitialLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitialLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.usernameInputLayout.error = getString(R.string.username_is_required)
        binding.passwordInputLayout.error = getString(R.string.password_is_required)

        binding.usernameInputLayout.addOnEditTextAttachedListener {
            binding.usernameInputLayout.isErrorEnabled = false
        }

        binding.passwordInputLayout.addOnEditTextAttachedListener {
            binding.passwordInputLayout.isErrorEnabled = false
        }

        binding.loginButton.setOnClickListener {
            var counter = 0
            if (binding.editTextUsername.text.toString() == "") {
                binding.usernameInputLayout.isErrorEnabled = true
            } else counter++

            if (binding.editTextPassword.text.toString() == "") {
                binding.passwordInputLayout.isErrorEnabled = true
            } else counter++

            if (counter == 2) {
                val username = binding.editTextUsername.text.toString()
                val password = binding.editTextPassword.text.toString()
                val sharedPreference =
                    getSharedPreferences("login_data_preference", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                editor.putString("username", username)
                editor.putString("password", password)
                editor.apply()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }



    }
}