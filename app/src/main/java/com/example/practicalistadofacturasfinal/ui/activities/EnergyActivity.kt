package com.example.practicalistadofacturasfinal.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.practicalistadofacturasfinal.MyApplication
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.RemoteConfigManager
import com.example.practicalistadofacturasfinal.data.AppRepository
import com.example.practicalistadofacturasfinal.databinding.ActivityEnergyBinding
import com.example.practicalistadofacturasfinal.ui.fragments.EnergyFirstFragment
import com.example.practicalistadofacturasfinal.ui.fragments.EnergySecondFragment
import com.example.practicalistadofacturasfinal.ui.fragments.EnergyThirdFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EnergyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnergyBinding
    @Inject lateinit var appRepository: AppRepository
    @Inject lateinit var remoteConfigManager: RemoteConfigManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        MyApplication()
        appRepository.fetchAndActivateConfig()
        showAppTheme()
        binding = ActivityEnergyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsets()
        setOnClickListener()

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

    private fun setOnClickListener() {
        binding.materialToolBar.setNavigationOnClickListener {
            startActivity(SelectionActivityM.create(this))
        }
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

    private fun showAppTheme() {
        remoteConfigManager.fetchAndActivateConfig()
        val showTheme = remoteConfigManager.getBooleanValue("showTheme")
        if (showTheme) {
            setTheme(R.style.MiTemaPersonalizado)
        }
    }
}