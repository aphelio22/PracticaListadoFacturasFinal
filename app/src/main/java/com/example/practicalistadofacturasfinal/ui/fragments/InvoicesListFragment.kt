package com.example.practicalistadofacturasfinal.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.data.retrofit.network.response.InvoiceResponse
import com.example.practicalistadofacturasfinal.databinding.FragmentInvoicesListBinding
import com.example.practicalistadofacturasfinal.ui.model.adapter.InvoiceAdapter
import com.example.practicalistadofacturasfinal.ui.model.adapter.PracticeAdapter
import com.example.practicalistadofacturasfinal.ui.viewmodel.InvoiceActivityViewModel

class InvoicesListFragment : Fragment() {
    private lateinit var binding: FragmentInvoicesListBinding
    private val viewModel: InvoiceActivityViewModel by viewModels()
    private lateinit var adapter: InvoiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvoicesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getInvoices().observe(viewLifecycleOwner) { invoices ->
            binding.rvInvoices.layoutManager = LinearLayoutManager(context)
            adapter = InvoiceAdapter(invoices) { practice ->
                onItemSelected(practice)
            }
            binding.rvInvoices.adapter = adapter
            val decoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
            binding.rvInvoices.addItemDecoration(decoration)
            Log.d("FACTURAS", invoices.toString())
            //adapter.submitList(invoices)
        }
    }

    private fun onItemSelected(practice: InvoiceResponse) {
    }
}