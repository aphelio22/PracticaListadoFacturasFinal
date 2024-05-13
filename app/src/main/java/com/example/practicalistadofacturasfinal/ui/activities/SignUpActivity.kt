package com.example.practicalistadofacturasfinal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listafacturaspractica.ui.view.FragmentPopUp
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.RemoteConfigManager
import com.example.practicalistadofacturasfinal.databinding.ActivitySignUpBinding
import com.example.practicalistadofacturasfinal.ui.viewmodel.SignUpActivityViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val signUpActivityViewModel: SignUpActivityViewModel by viewModels()
    private var remoteConfigManager: RemoteConfigManager = RemoteConfigManager.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        remoteConfigManager.fetchAndActivateConfig()
        showAppTheme()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsets()

        signUpActivityViewModel.signUpResult.observe(this) { result ->
            result?.let {
                if (it.isSuccess) {
                    val miIntent = Intent(this, LoginActivity::class.java)
                    startActivity(miIntent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        it.exceptionOrNull()?.localizedMessage ?: "Unknown error occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.btRegister.setOnClickListener {
            val email = binding.etEmailSignUp.editText?.text.toString()
            val password = binding.etPassSignUp.editText?.text.toString()
            val confirmPassword = binding.etRepeatPassSignUp.editText?.text.toString()

            if (validateInputs(email, password, confirmPassword)) {
                signUpActivityViewModel.signUp(email, password, confirmPassword)
            } else if (password != confirmPassword && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                val fragmentManager = supportFragmentManager
                val customPopupFragment = FragmentPopUp(getString(R.string.blankFields_FragmentPopUp))
                customPopupFragment.show(fragmentManager, "FragmentPopUp")
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

    private fun showAppTheme() {
        remoteConfigManager.fetchAndActivateConfig()
        val showTheme = remoteConfigManager.getBooleanValue("showTheme")
        if (showTheme) {
            setTheme(R.style.MiTemaPersonalizado)
        }
    }
}