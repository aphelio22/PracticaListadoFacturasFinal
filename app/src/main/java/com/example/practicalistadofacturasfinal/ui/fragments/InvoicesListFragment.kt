package com.example.practicalistadofacturasfinal.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom
import com.example.practicalistadofacturasfinal.databinding.FragmentInvoicesListBinding
import com.example.practicalistadofacturasfinal.ui.model.adapter.InvoiceAdapter
import com.example.practicalistadofacturasfinal.ui.viewmodel.InvoiceActivityViewModel

class InvoicesListFragment : Fragment() {
    private lateinit var binding: FragmentInvoicesListBinding
    private lateinit var adapter: InvoiceAdapter
    private val viewModel: InvoiceActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initRepository()
        viewModel.initFetchUseCase()
        viewModel.fetchInvoices()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvoicesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClickListener()
        viewModel.invoiceLiveData.observe(viewLifecycleOwner) { invoices ->

            binding.rvInvoices.layoutManager = LinearLayoutManager(context)
            adapter = InvoiceAdapter(invoices) { inv ->
                onItemSelected(inv)
            }
            binding.rvInvoices.adapter = adapter
            val decoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
            binding.rvInvoices.addItemDecoration(decoration)
            Log.d("FACTURAS", invoices.toString())
            //adapter.submitList(invoices)
        }
        //viewModel.getInvoices()
    }

    private fun onItemSelected(practice: InvoiceModelRoom) {

    }

    private fun setOnClickListener() {
        binding.materialToolBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.invoiceMenuMain -> {
                    val action = InvoicesListFragmentDirections.actionInvoicesListFragmentToInvoicesFiltersFragment()
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }
        }
    }
}