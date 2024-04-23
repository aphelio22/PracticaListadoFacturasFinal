package com.example.practicalistadofacturasfinal.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.databinding.ActivityEnergyBinding
import com.example.practicalistadofacturasfinal.ui.fragments.EnergyFirstFragment
import com.example.practicalistadofacturasfinal.ui.fragments.EnergySecondFragment
import com.example.practicalistadofacturasfinal.ui.fragments.EnergyThirdFragment
import com.google.android.material.tabs.TabLayout

class EnergyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnergyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEnergyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsets()

        binding.tlEnergy.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    when(tab.position) {
                        0 -> replaceFragment(EnergyFirstFragment())
                        1 -> replaceFragment(EnergySecondFragment())
                        2 -> replaceFragment(EnergyThirdFragment())
                    }
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                //Empty function
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
                //Empty function
            }
        })
        replaceFragment(EnergyFirstFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
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