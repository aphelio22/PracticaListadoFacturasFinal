package com.example.practicalistadofacturasfinal.ui.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.MyApplication
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.constants.Constants
import com.example.practicalistadofacturasfinal.data.InvoiceRepository
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom
import com.example.practicalistadofacturasfinal.domain.FetchInvoicesUseCase
import com.example.practicalistadofacturasfinal.ui.model.FilterVO
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InvoiceActivityViewModel() : ViewModel() {
    private lateinit var invoiceRepository: InvoiceRepository
    private lateinit var fetchInvoicesUseCase: FetchInvoicesUseCase

    private var invoices: List<InvoiceModelRoom> = emptyList()

    private val _filteredInvoicesLiveData = MutableLiveData<List<InvoiceModelRoom>>()
    val filteredInvoicesLiveData: LiveData<List<InvoiceModelRoom>>
        get() = _filteredInvoicesLiveData

    private var _maxAmount: Float = 0.0f
    var maxAmount = 0.0f
        get() = _maxAmount

    private var _filterLiveData = MutableLiveData<FilterVO>()
    val filterLiveData: LiveData<FilterVO>
        get() = _filterLiveData

    private var useAPI = false

    init {
        initRepository()
        initFetchUseCase()
        fetchInvoices()
    }

    fun fetchInvoices() {
        viewModelScope.launch {
            _filteredInvoicesLiveData.postValue(invoiceRepository.getAllInvoices())
            try {
                if (isInternetAvailable()) {
                    when (useAPI) {
                        true -> invoiceRepository.fetchAndInsertInvoicesFromAPI()
                        false -> invoiceRepository.fetchAndInsertInvoicesFromMock()
                    }
                    invoices = invoiceRepository.getAllInvoices()
                    _filteredInvoicesLiveData.postValue(invoices)
                    findMaxAmount()
                    //verifyFilters()
                }
            } catch (e: Exception) {
                Log.d("Error", e.printStackTrace().toString())
            }
        }
    }

    fun initRepository() {
        invoiceRepository = InvoiceRepository()
    }

    fun initFetchUseCase() {
        fetchInvoicesUseCase = FetchInvoicesUseCase(invoiceRepository)
    }

    fun switchMode(apiMode: Boolean) {
        invoiceRepository.invoiceDAO.deleteAllInvoices()
        useAPI = apiMode
        fetchInvoices()
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        return capabilities != null && (
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                )
    }

    fun findMaxAmount() {
        var max = 0.0

        for (invoice in invoices) {
            val actualInvoiceAmount = invoice.importeOrdenacion
            if (max < actualInvoiceAmount) {
                max = actualInvoiceAmount
            }
        }
        _maxAmount = max.toFloat()
    }

    fun applyFilters(
        maxDate: String,
        minDate: String,
        maxValueSlider: Double,
        status: HashMap<String, Boolean>
    ) {
        if ((minDate == getString(
                MyApplication.context,
                R.string.dayMonthYear
            ) && maxDate == getString(MyApplication.context, R.string.dayMonthYear)) ||
            (minDate != getString(
                MyApplication.context,
                R.string.dayMonthYear
            ) && maxDate != getString(MyApplication.context, R.string.dayMonthYear))
        ) {
            val filter = FilterVO(maxDate, minDate, maxValueSlider, status)
            _filterLiveData.postValue(filter)
        } else {
            // Si alguna de las dos fechas, o las dos, no equivale a "Dia/Mes/Año", se realiza el intent, sino salta el PopUp.
        }
    }

    fun verifyFilters() {
        var filteredList = verifyDateFilter()
        filteredList = verifyCheckBox(filteredList)
        filteredList = verifyBalanceBar(filteredList)
        _filteredInvoicesLiveData.postValue(filteredList)
    }

    private fun verifyDateFilter(): List<InvoiceModelRoom> {
        val maxDate = filterLiveData.value?.maxDate
        val minDate = filterLiveData.value?.minDate
        val filteredList = ArrayList<InvoiceModelRoom>()
        if (minDate != getString(
                MyApplication.context,
                R.string.dayMonthYear
            ) && maxDate != getString(MyApplication.context, R.string.dayMonthYear)
        ) {
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            //Variables locales del método en las que se almacenan la fecha mínima y la fecha máxima parseadas.
            var minDateLocal: Date? = null
            var maxDateLocal: Date? = null

            try {
                //Parseo de las fechas.
                minDateLocal = minDate?.let { simpleDateFormat.parse(it) }
                maxDateLocal = maxDate?.let { simpleDateFormat.parse(it) }
            } catch (e: ParseException) {
                Log.d("Error1: ", "comprobarFiltroFechas: ParseException")
            }
            for (invoice in invoices) {
                var invoiceDate = Date()
                try {
                    invoiceDate = simpleDateFormat.parse(invoice.fecha)!!
                } catch (e: ParseException) {
                    Log.d("Error2: ", "comprobarFiltroFechas: ParseException")
                }
                //Se verifica si la fecha de la factura está dentro del intervalo especificado.
                if (invoiceDate.after(minDateLocal) && invoiceDate.before(maxDateLocal)) {
                    filteredList.add(invoice)
                }
            }
        }
        return filteredList
    }

    private fun verifyCheckBox(
        filteredInvoices: List<InvoiceModelRoom>?
    ): List<InvoiceModelRoom> {
        var filteredInvoicesCheckBox = ArrayList<InvoiceModelRoom>()
        val status = filterLiveData.value?.status
        //Se obtienen los estados de las CheckBoxes.
        val checkBoxPaid = status?.get(Constants.PAID_STRING) ?: false
        val checkBoxCanceled = status?.get(Constants.CANCELED_STRING) ?: false
        val checkBoxFixedPayment = status?.get(Constants.FIXED_PAYMENT_STRING) ?: false
        val checkBoxPendingPayment = status?.get(Constants.PENDING_PAYMENT_STRING) ?: false
        val checkBoxPaymentPlan = status?.get(Constants.PAYMENT_PLAN_STRING) ?: false

        //Lista que contendrá las facturas filtradas por estado.


        if (checkBoxPaid || checkBoxCanceled || checkBoxFixedPayment || checkBoxPendingPayment || checkBoxPaymentPlan) {
            //Verificación de los estados de las facturas y los CheckBoxes seleccionados.
            for (invoice in filteredInvoices ?: emptyList()) {
                val invoiceState = invoice.descEstado
                val isPaid = invoiceState == "Pagada"
                val isCanceled = invoiceState == "Anuladas"
                val isFixedPayment = invoiceState == "cuotaFija"
                val isPendingPayment = invoiceState == "Pendiente de pago"
                val isPaymentPlan = invoiceState == "planPago"

                //Se añade la factura a la lista filtrada si cumple con alguno de los estados seleccionados.
                if ((isPaid && checkBoxPaid) || (isCanceled && checkBoxCanceled) || (isFixedPayment && checkBoxFixedPayment) || (isPendingPayment && checkBoxPendingPayment) || (isPaymentPlan && checkBoxPaymentPlan)) {
                    filteredInvoicesCheckBox.add(invoice)
                }
            }
            return filteredInvoicesCheckBox
        } else {
            return filteredInvoices ?: emptyList()
        }
    }

    private fun verifyBalanceBar(filteredList: List<InvoiceModelRoom>): List<InvoiceModelRoom> {
    var filteredInvoicesBalanceBar = ArrayList<InvoiceModelRoom>()
        val maxValueSlider = filterLiveData.value?.maxValueSlider
        for (factura in filteredList) {
            //Se añade la factura a la lista filtrada si su importe es menor que el valor seleccionado.
            if (factura.importeOrdenacion!! < maxValueSlider!!) {
                filteredInvoicesBalanceBar.add(factura)
            }
        }
        return filteredInvoicesBalanceBar
    }
}