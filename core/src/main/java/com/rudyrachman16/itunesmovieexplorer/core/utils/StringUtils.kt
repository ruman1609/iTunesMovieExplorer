package com.rudyrachman16.itunesmovieexplorer.core.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.MissingResourceException

object StringUtils {
    fun String.getCountryCode2WordsOnly(): String {
        val locales = Locale.getAvailableLocales()
        val find = locales.find {
            try {
                it.isO3Country == this
            } catch (e: MissingResourceException) {
                it.country == this
            }
        } ?: return ""
        return find.country.lowercase()
    }

    fun String.releaseDateToReadable(): String {
        val sdfRFC = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val sdf = SimpleDateFormat("dd MMMM yyyy, HH.mm", Locale.getDefault())
        try {
            return sdfRFC.parse(this)?.let { sdf.format(it) } ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun Long.inMillisToString(): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val calendar = Calendar.getInstance().apply { timeInMillis = this@inMillisToString }
        return sdf.format(calendar.time)
    }

    fun Number.toMoneyFormat(): String = NumberFormat.getCurrencyInstance().format(this)
}