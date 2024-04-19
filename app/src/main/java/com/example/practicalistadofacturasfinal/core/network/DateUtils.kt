package com.example.practicalistadofacturasfinal.core.network


import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.toLocalDate(pattern: String): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))
}

fun LocalDate.toDateString(pattern: String): String {
    return this.format(DateTimeFormatter.ofPattern(pattern))
}