package com.example.practicalistadofacturasfinal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.RemoteConfigManager
import com.example.practicalistadofacturasfinal.data.AppRepository
import com.example.practicalistadofacturasfinal.databinding.ActivityForgotPassBinding
import com.example.practicalistadofacturasfinal.ui.viewmodel.ForgotPassViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPassBinding
    private val forgotPassViewModel: ForgotPassViewModel by viewModels()
    @Inject lateinit var appRepository: AppRepository
    @Inject lateinit var remoteConfigManager: RemoteConfigManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        appRepository.fetchAndActivateConfig()
        showAppTheme()
        binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsets()

        binding.btResetPass.setOnClickListener {
            val email = binding.etEmailReset.editText?.text.toString()
            if (forgotPassViewModel.isEmailValid(email)) {
                forgotPassViewModel.resetPassword(email)
            }  else {
                Toast.makeText(this, "Por favor, introduzca un correo electrónico válido", Toast.LENGTH_SHORT).show()
            }
        }

        forgotPassViewModel.resetPassResult.observe(this) { result ->
            result?.let {
                if (it.isSuccess) {
                    // Envío de correo electrónico de restablecimiento de contraseña exitoso
                    Toast.makeText(
                        this,
                        "Correo electrónico de restablecimiento enviado",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Error al enviar el correo electrónico de restablecimiento de contraseña
                    Toast.makeText(
                        this,
                        "Error: ${it.exceptionOrNull()?.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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

    private fun showAppTheme() {
        remoteConfigManager.fetchAndActivateConfig()
        val showTheme = remoteConfigManager.getBooleanValue("showTheme")
        if (showTheme) {
            setTheme(R.style.MiTemaPersonalizado)
        }
    }
}