package com.example.practicalistadofacturasfinal.constants

class Constants {
    companion object {
        //Claves de los CheckBoxes.
        /**
         * Clave utilizada para el CheckBox asociado a las facturas
         * con el estado 'pagadas'.
         */
        const val PAID_STRING: String = "PAID_STRING"

        /**
         * Clave utilizada para el CheckBox asociado a las facturas
         * con el estado 'anuladas'.
         */
        const val CANCELED_STRING: String = "CANCELED_STRING"

        /**
         * Clave utilizada para el CheckBox asociado a las facturas
         * con el estado 'plan de pago'.
         */
        const val FIXED_PAYMENT_STRING: String = "FIXED_PAYMENT_STRING"

        /**
         * Clave utilizada para el CheckBox asociado a las facturas
         * con el estado 'pendiente de pago'.
         */
        const val PENDING_PAYMENT_STRING: String = "PENDING_PAYMENT_STRING"

        /**
         * Clave utilizada para el CheckBox asociado a las facturas
         * con el estado 'plan de pago'.
         */
        const val PAYMENT_PLAN_STRING: String = "PAYMENT_PLAN_STRING"

        //Claves de Intent.
        /**
         * Clave del Intent que se utiliza para enviar/recibir datos de los filtros
         * entre MainActivity y FilterActivity.
         * */
        const val SEND_RECEIVE_FILTERS: String = "SEND_RECEIVE_FILTERS"

        /**
         * Clave uilizada para obtener el máximo importe de las facturas.
         */
        const val MAX_AMOUNT: String = "MAX_AMOUNT"

        //Claves demSharedPreferences.
        /**
         * Clave utilizada en las SheredPreferences de la clase FilterActivity
         * para almacenar la información de los filtros seleccionados.
         * */
        const val FILTER_STATE: String = "FILTER_STATE"
    }
}