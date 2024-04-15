package com.example.practicalistadofacturasfinal.ui.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.practicalistadofacturasfinal.MyApplication
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.databinding.FragmentInvoicesFiltersBinding
import com.example.practicalistadofacturasfinal.databinding.FragmentInvoicesListBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class InvoicesFiltersFragment : Fragment() {
    private lateinit var binding: FragmentInvoicesFiltersBinding
    private var maxAmount: Int = 0
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
        initComponents()
    }

    private fun initComponents() {
        initCalendar()
        initSeekBar()
        initCheckBoxes()
        initApplyFiltersButton()
        initResetFilterButton()
    }

    private fun initResetFilterButton() {

    }

    private fun initApplyFiltersButton() {

    }

    private fun initCheckBoxes() {

    }

    private fun initSeekBar() {

    }

    private fun initCalendar() {
        initMinDateButton()
        initMaxDateButton()
    }

    @SuppressLint("SetTextI18n")
    private fun initMinDateButton() {
        binding.btMinDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = context?.let { it1 ->
                DatePickerDialog(
                    it1,
                    { view, year1, monthOfYear, dayOfMonth ->
                        binding.btMinDate.text = "$dayOfMonth/${monthOfYear + 1}/$year1"
                    },
                    year,
                    month,
                    day
                )
            }
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val maxDateLocal = binding.btMaxDate.text.toString()
            val maxDate: Date
            try {
                maxDate = simpleDateFormat.parse(maxDateLocal)!!
                datePickerDialog?.datePicker?.maxDate = maxDate.time
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            datePickerDialog?.show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initMaxDateButton() {
        binding.btMaxDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = context?.let { it1 ->
                DatePickerDialog(
                    it1,
                    { view, year1, monthOfYear, dayOfMonth ->
                        binding.btMaxDate.text = "$dayOfMonth/${monthOfYear + 1}/$year1"
                    },
                    year,
                    month,
                    day
                )
            }

            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val minDateLocal = binding.btMinDate.text.toString()
            val minDate: Date
            try {
                minDate = simpleDateFormat.parse(minDateLocal)!!
                datePickerDialog?.datePicker?.minDate = minDate.time
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            datePickerDialog?.show()
        }
    }

    private fun setOnClickListener() {
        binding.materialToolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.invoiceMenuFilter -> {
                    val action =
                        InvoicesFiltersFragmentDirections.actionInvoicesFiltersFragmentToInvoicesListFragment()
                    findNavController().navigate(action)
                    true
                }

                else -> false
            }
        }
    }
}