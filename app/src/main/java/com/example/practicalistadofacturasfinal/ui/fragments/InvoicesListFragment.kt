package com.example.practicalistadofacturasfinal.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listafacturaspractica.ui.view.FragmentPopUp
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.RemoteConfigManager
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom
import com.example.practicalistadofacturasfinal.databinding.FragmentInvoicesListBinding
import com.example.practicalistadofacturasfinal.ui.activities.SelectionActivityM
import com.example.practicalistadofacturasfinal.ui.model.FilterVO
import com.example.practicalistadofacturasfinal.ui.model.adapter.InvoiceAdapter
import com.example.practicalistadofacturasfinal.ui.viewmodel.InvoiceActivityViewModel

class InvoicesListFragment : Fragment() {
    private lateinit var binding: FragmentInvoicesListBinding
    private lateinit var adapter: InvoiceAdapter
    private val viewModel: InvoiceActivityViewModel by activityViewModels()
    private lateinit var remoteConfigManager: RemoteConfigManager
    private var maxAmount = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvoicesListBinding.inflate(layoutInflater, container, false)
        RemoteConfigManager().fetchAndActivateConfig()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClickListener()
        InitViewModel()

        //viewModel.getInvoices()
        binding.switchRetromock.setOnClickListener {
            if (binding.switchRetromock.isChecked) {
                viewModel.switchMode(true)
            } else {
                viewModel.switchMode(false)
            }
        }

    }

    private fun InitViewModel() {
        viewModel.filteredInvoicesLiveData.observe(viewLifecycleOwner) { invoices ->

            remoteConfigManager = RemoteConfigManager.getInstance()
            val showRetroSwitch = remoteConfigManager.getBooleanValue("showSwitch")
            binding.switchRetromock.visibility = if (showRetroSwitch) View.VISIBLE else View.GONE

            if (invoices.isEmpty()) {
                binding.tvEmptyList.visibility = View.VISIBLE
            } else {
                binding.tvEmptyList.visibility = View.GONE
            }

            InitRecyclerView(invoices)
            InitDecoration()
        }
        viewModel.filterLiveData.observe(viewLifecycleOwner) {filter ->
            if (filter != null) {
               viewModel.verifyFilters()
            }
        }
    }

    private fun InitDecoration() {
        val decoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        binding.rvInvoices.addItemDecoration(decoration)
    }

    private fun InitRecyclerView(invoices: List<InvoiceModelRoom>) {
        binding.rvInvoices.layoutManager = LinearLayoutManager(context)
        adapter = InvoiceAdapter(invoices) { inv ->
            onItemSelected(inv)
        }
        binding.rvInvoices.adapter = adapter
    }

    private fun onItemSelected(practice: InvoiceModelRoom) {
        val fragmentManager = requireActivity().supportFragmentManager
        val customPopupFragment = FragmentPopUp(getString(R.string.notImplementedYet))
        customPopupFragment.show(fragmentManager, "FragmentPopUp")
    }

    private fun setOnClickListener() {
        binding.materialToolBar.setNavigationOnClickListener {
            startActivity(SelectionActivityM.Companion.create(requireContext()))
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