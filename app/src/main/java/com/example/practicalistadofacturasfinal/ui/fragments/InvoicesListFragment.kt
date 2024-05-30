package com.example.practicalistadofacturasfinal.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom
import com.example.practicalistadofacturasfinal.databinding.FragmentInvoicesListBinding
import com.example.practicalistadofacturasfinal.enums.ApiType
import com.example.practicalistadofacturasfinal.ui.activities.SelectionActivityM
import com.example.practicalistadofacturasfinal.ui.model.adapter.InvoiceAdapter
import com.example.practicalistadofacturasfinal.ui.viewmodel.InvoiceActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

class InvoicesListFragment : Fragment() {
    private lateinit var binding: FragmentInvoicesListBinding
    private lateinit var adapter: InvoiceAdapter
    private val viewModel: InvoiceActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvoicesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClickListener()
        initViewModel()

        binding.toggleRetroKtor.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button_retromock -> viewModel.setSelectedApiType(ApiType.RETROMOCK)
                    R.id.button_retrofit -> viewModel.setSelectedApiType(ApiType.RETROFIT)
                    R.id.button_ktor -> viewModel.setSelectedApiType(ApiType.KTOR)
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel.filteredInvoicesLiveData.observe(viewLifecycleOwner) { invoices ->
            if (invoices.isEmpty()) {
                binding.tvEmptyList.visibility = View.VISIBLE
            } else {
                binding.tvEmptyList.visibility = View.GONE
            }
            initRecyclerView(invoices)
        }

        viewModel.filterLiveData.observe(viewLifecycleOwner) {filter ->
            if (filter != null) {
               viewModel.verifyFilters()
            }
        }

        viewModel.showRemoteConfig.observe(viewLifecycleOwner) {showSwitch ->
            if (showSwitch) {
                binding.toggleRetroKtor.visibility = View.VISIBLE
            } else {
                binding.toggleRetroKtor.visibility = View.GONE
            }
        }
        initDecoration()
    }

    private fun initDecoration() {
        val decoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        binding.rvInvoices.addItemDecoration(decoration)
    }

    private fun initRecyclerView(invoices: List<InvoiceModelRoom>) {
        binding.rvInvoices.layoutManager = LinearLayoutManager(context)
        adapter = InvoiceAdapter(invoices) {
            onItemSelected()
        }
        binding.rvInvoices.adapter = adapter
    }

    private fun onItemSelected() {
        val fragmentManager = requireActivity().supportFragmentManager
        val customPopupFragment = FragmentPopUp(getString(R.string.notImplementedYet))
        customPopupFragment.show(fragmentManager, "FragmentPopUp")
    }

    private fun setOnClickListener() {
        binding.materialToolBar.setNavigationOnClickListener {
            startActivity(SelectionActivityM.create(requireContext()))
        }

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