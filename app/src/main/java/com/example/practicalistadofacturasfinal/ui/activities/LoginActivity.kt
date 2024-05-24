package com.example.practicalistadofacturasfinal.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.practicalistadofacturasfinal.ui.fragments.FragmentPopUp
import com.example.practicalistadofacturasfinal.MyApplication
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.RemoteConfigManager
import com.example.practicalistadofacturasfinal.constants.Constants
import com.example.practicalistadofacturasfinal.data.AppRepository
import com.example.practicalistadofacturasfinal.databinding.ActivityLoginBinding
import com.example.practicalistadofacturasfinal.ui.viewmodel.LoginActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginActivityViewModel: LoginActivityViewModel by viewModels()
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var encryptedPrefs: SharedPreferences
    @Inject lateinit var appRepository: AppRepository
    @Inject lateinit var appContext: Context
    @Inject lateinit var remoteConfigManager: RemoteConfigManager

    private fun createEncryptedPreferences(context: Context): SharedPreferences {
        val masterKeyAlias = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        return EncryptedSharedPreferences.create(
            context,
            Constants.PREFS_FILE_NAME,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        appRepository.fetchAndActivateConfig()
        showAppTheme()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsets()

        encryptedPrefs = createEncryptedPreferences(appContext)
        editor = encryptedPrefs.edit()

        val logOut = intent.getBooleanExtra("logOut", false)

        if (logOut) {
            editor.clear()
            saveOnSharedPreferences("", "")
        } else {
            setValuesIfExist()
        }

        val email = binding.etEmailUser.editText?.text.toString()
        val password = binding.etLoginPass.editText?.text.toString()
        if (loginActivityViewModel.isLoginInfoValid(email, password)) {
            attemptLogin(email, password)
        }

        loginActivityViewModel.loginResult.observe(this) { result ->
            result?.let {
                if (it.isSuccess) {
                    val intent = Intent(this, SelectionActivityM::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val fragmentManager = supportFragmentManager
                    val customPopupFragment =
                        FragmentPopUp(getString(R.string.emailOrPassDoesNotMatch_FragmentPopUp))
                    customPopupFragment.show(fragmentManager, "FragmentPopUp")
                }
            }
        }

        binding.btLogin.setOnClickListener {
            val email = binding.etEmailUser.editText?.text.toString()
            val password = binding.etLoginPass.editText?.text.toString()
            attemptLogin(email, password)
        }

        binding.btRegister.setOnClickListener {
            val miIntent = Intent(this, SignUpActivity::class.java)
            startActivity(miIntent)
            finish()
        }

        binding.tvForgetPass.setOnClickListener {
            val miIntent = Intent(this, ForgotPassActivity::class.java)
            startActivity(miIntent)
            finish()
        }
    }

    private fun showAppTheme() {
        appRepository.fetchAndActivateConfig()
        val showTheme = appRepository.getBooleanValue("showTheme")
        if (showTheme) {
            setTheme(R.style.MiTemaPersonalizado)
        }
    }

    private fun attemptLogin(email: String, password: String) {
        if (loginActivityViewModel.isLoginInfoValid(email, password)) {
            loginActivityViewModel.login(email, password)
            saveOnSharedPreferences(email, password)
        } else {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveOnSharedPreferences(email: String, password: String) {
        if (binding.cbLoginRemember.isChecked) {
            editor.putString("email", email)
            editor.putString("password", password)
            editor.putBoolean("recordar", true)
            editor.apply()
        } else {
            editor.clear()
            editor.putBoolean("recordar", false)
            editor.apply()
        }
    }

    private fun setValuesIfExist() {
        val email = encryptedPrefs.getString("email", "")
        val password = encryptedPrefs.getString("password", "")
        val remember = encryptedPrefs.getBoolean("recordar", false)
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            binding.etLoginPass.editText?.setText(password)
            binding.etEmailUser.editText?.setText(email)
            binding.cbLoginRemember.isChecked = remember
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