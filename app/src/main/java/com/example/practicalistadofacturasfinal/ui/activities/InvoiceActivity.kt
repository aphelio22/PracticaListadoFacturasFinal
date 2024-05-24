package com.example.practicalistadofacturasfinal.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicalistadofacturasfinal.MyApplication
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.RemoteConfigManager
import com.example.practicalistadofacturasfinal.data.AppRepository
import com.example.practicalistadofacturasfinal.databinding.ActivityInvoiceBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InvoiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInvoiceBinding
    @Inject lateinit var appRepository: AppRepository
    @Inject lateinit var remoteConfigManager: RemoteConfigManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        MyApplication()
        appRepository.fetchAndActivateConfig()
        showAppTheme()
        binding = ActivityInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsets()

    }

    private fun setInsets() {
        resources.getDimension(R.dimen.activities_fragments_padding).toInt()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left , systemBars.top , systemBars.right, + systemBars.bottom )
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