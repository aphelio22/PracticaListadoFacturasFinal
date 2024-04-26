package com.example.practicalistadofacturasfinal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.databinding.ActivitySignUpBinding
import com.example.practicalistadofacturasfinal.ui.viewmodel.SignUpActivityViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import kotlin.system.measureTimeMillis

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val signUpActivityViewModel: SignUpActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsets()

        binding.btRegister.setOnClickListener {
            val email = binding.etEmailSignUp.text.toString()
            val password = binding.etPassSignUp.text.toString()
            val confirmPassword = binding.etRepeatPassSignUp.text.toString()

            if (validateInputs(email, password, confirmPassword)) {
                // Llamar al método de registro en el ViewModel
                signUpActivityViewModel.signUp(email, password, confirmPassword,
                    onSuccess = {
                        val miIntent = Intent(this, LoginActivity::class.java)
                        startActivity(miIntent)
                        finish()
                    },
                    onError = { errorMessage ->
                        // Por ejemplo:
                            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btReturnLogin.setOnClickListener {
            val miIntent = Intent(this, LoginActivity::class.java)
            startActivity(miIntent)
            finish()
        }
    }

    private fun setInsets() {
        val padding = resources.getDimension(R.dimen.activities_fragments_padding).toInt()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left + padding, systemBars.top + padding, systemBars.right + padding, systemBars.bottom + padding)
            insets
        }
    }

    private fun validateInputs(email: String, password: String, confirmPassword: String): Boolean {
        // Realizar validaciones aquí, por ejemplo:
        return email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword
    }
}