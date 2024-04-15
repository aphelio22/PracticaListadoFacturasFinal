package com.example.practicalistadofacturasfinal.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.databinding.FragmentInvoicesFiltersBinding
import com.example.practicalistadofacturasfinal.databinding.FragmentInvoicesListBinding

class InvoicesFiltersFragment : Fragment() {
    private lateinit var binding: FragmentInvoicesFiltersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInvoicesFiltersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.materialToolBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.invoiceMenuFilter -> {
                    val action = InvoicesFiltersFragmentDirections.actionInvoicesFiltersFragmentToInvoicesListFragment()
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }
        }
    }
}