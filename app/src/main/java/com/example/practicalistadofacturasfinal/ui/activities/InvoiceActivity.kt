package com.example.practicalistadofacturasfinal.ui.activities

import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicalistadofacturasfinal.MyApplication
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.databinding.ActivityInvoiceBinding

class InvoiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInvoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        MyApplication()
        binding = ActivityInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsets()

    }

    private fun setInsets() {
        val padding = resources.getDimension(R.dimen.activities_fragments_padding).toInt()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left , systemBars.top , systemBars.right, + systemBars.bottom )
            insets
        }
    }
}