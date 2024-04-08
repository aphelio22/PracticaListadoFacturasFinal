package com.example.practicalistadofacturasfinal.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.databinding.ActivityMainBinding
import com.example.practicalistadofacturasfinal.ui.model.PracticeVO
import com.example.practicalistadofacturasfinal.ui.model.adapter.PracticeAdapter

class SelectionActivityM : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var practiceList: List<PracticeVO>
    private lateinit var adapter: PracticeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setInsets()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        practiceList = (listOf(PracticeVO(1, "Práctica 1"), PracticeVO(2, "Práctica 2")))
        binding.rvPracticeSelection.layoutManager = LinearLayoutManager(this)
        adapter = PracticeAdapter(practiceList) { practice ->
            onItemSelected(practice)
        }
        binding.rvPracticeSelection.adapter = adapter
        val decoration = DividerItemDecoration(this@SelectionActivityM, RecyclerView.VERTICAL)
        binding.rvPracticeSelection.addItemDecoration(decoration)
    }

    private fun setInsets() {
        val padding = resources.getDimension(R.dimen.activities_fragments_padding).toInt()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left + padding, systemBars.top + padding, systemBars.right + padding, systemBars.bottom + padding)
            insets
        }
    }

    private fun onItemSelected(practiceVO: PracticeVO) {
        Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show()
    }
}