package com.example.practicalistadofacturasfinal.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.databinding.ActivityEnergyBinding

class EnergyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnergyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEnergyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsets()
    }

    private fun setInsets() {
        val padding = resources.getDimension(R.dimen.activity_energy).toInt()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left + padding, systemBars.top + padding, systemBars.right + padding, systemBars.bottom + padding)
            insets
        }
    }
}