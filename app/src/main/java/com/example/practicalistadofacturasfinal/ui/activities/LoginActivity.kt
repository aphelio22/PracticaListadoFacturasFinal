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
import com.example.listafacturaspractica.ui.view.FragmentPopUp
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.databinding.ActivityLoginBinding
import com.example.practicalistadofacturasfinal.ui.viewmodel.LoginActivityViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginAvtivityViewModel: LoginActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsets()
        binding.btLogin.setOnClickListener {
            val email = binding.etEmailUser.text.toString()
            val password = binding.etLoginPass.text.toString()

            loginAvtivityViewModel.login(email, password,
                onSuccess = {
                    val intent = Intent(this, SelectionActivityM::class.java)
                    startActivity(intent)
                    finish()
                },
                onError = { errorMessage ->
                    val fragmentManager = supportFragmentManager
                    val customPopupFragment = FragmentPopUp(getString(R.string.emailOrPassDoesNotMatch_FragmentPopUp))
                    customPopupFragment.show(fragmentManager, "FragmentPopUp")
                }
            )
        }
    }

    private fun setInsets() {
        val padding = resources.getDimension(R.dimen.activities_fragments_padding).toInt()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + padding,
                systemBars.top + padding,
                systemBars.right + padding,
                systemBars.bottom + padding
            )
            insets
        }
    }
}