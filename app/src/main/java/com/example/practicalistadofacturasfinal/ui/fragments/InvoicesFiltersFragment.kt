package com.example.practicalistadofacturasfinal.ui.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.constants.Constants
import com.example.practicalistadofacturasfinal.databinding.FragmentInvoicesFiltersBinding
import com.example.practicalistadofacturasfinal.ui.viewmodel.InvoiceActivityViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class InvoicesFiltersFragment : Fragment() {
    private lateinit var binding: FragmentInvoicesFiltersBinding
    private val viewModel: InvoiceActivityViewModel by activityViewModels()
    private lateinit var paid: CheckBox
    private lateinit var canceled: CheckBox
    private lateinit var fixedPayment: CheckBox
    private lateinit var pendingPayment: CheckBox
    private lateinit var paymentPlan: CheckBox
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
        binding.btApplyFilters.setOnClickListener {
            val maxValueSlider = binding.seekBarValue.text.toString().toDouble()

            val status = hashMapOf(
                Constants.PAID_STRING to paid.isChecked,
                Constants.CANCELED_STRING to canceled.isChecked,
                Constants.FIXED_PAYMENT_STRING to fixedPayment.isChecked,
                Constants.PENDING_PAYMENT_STRING to pendingPayment.isChecked,
                Constants.PAYMENT_PLAN_STRING to paymentPlan.isChecked
            )

            val minDate = binding.btMinDate.text.toString()
            val maxDate = binding.btMaxDate.text.toString()

            viewModel.applyFilters(maxDate, minDate, maxValueSlider, status)
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun initCheckBoxes() {
        paid = binding.cbPaid
        canceled = binding.cbCanceled
        fixedPayment = binding.cbFixedPayment
        pendingPayment = binding.cbPendingPayment
        paymentPlan = binding.cbPaymentPlan
    }

    private fun initSeekBar() {
        val maxAmount = viewModel.maxAmount.toInt() + 1
        binding.seekBar.max = maxAmount
        binding.tvMaxSeekbar.text = "$maxAmount"
        binding.tvMinSeekbar.text = "0"
        binding.seekBarValue.text = "$maxAmount"
        binding.seekBar.progress = viewModel.maxAmount.toInt()

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.seekBarValue.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //Sin función
                Log.d("onStartTrackingTouch()", "onStartTrackingTouch: el método ha fallado.")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                //Sin función
                Log.d("onStopTrackingTouch()", "onStopTrackingTouch: el método ha fallado.")
            }
        })
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
            when(it.itemId) {
                R.id.invoiceMenuFilter -> {
                    requireActivity().supportFragmentManager.popBackStack()
                    true
                }
                else -> false
            }
        }
    }
}
