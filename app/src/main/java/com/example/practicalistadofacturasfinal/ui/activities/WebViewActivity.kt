package com.example.practicalistadofacturasfinal.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.RemoteConfigManager
import com.example.practicalistadofacturasfinal.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    private var remoteConfigManager: RemoteConfigManager = RemoteConfigManager.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        remoteConfigManager.fetchAndActivateConfig()
        showAppTheme()
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setInsets()

        val webView: WebView = binding.webView

        binding.btnOpenWebView.setOnClickListener {
           webView.loadUrl("https://www.iberdrola.es")
        }

        binding.btnExternalWeb.setOnClickListener {
            val miIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.iberdrola.es"))
            startActivity(miIntent)
        }
    }

    private fun setInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_web)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
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