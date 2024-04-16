package com.example.practicalistadofacturasfinal.ui.model

data class FilterVO (
    var maxDate: String,
    var minDate: String,
    var maxValueSlider: Double,
    var status: HashMap<String, Boolean>
)