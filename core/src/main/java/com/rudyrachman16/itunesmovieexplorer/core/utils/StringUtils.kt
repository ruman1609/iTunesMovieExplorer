package com.rudyrachman16.itunesmovieexplorer.core.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.MissingResourceException

object StringUtils {
    /**
     * Convert country code from API Response into Locale.country so can access flag resource
     * @return lowercase of country code (ISO Alpha-2)
     */
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

    /**
     * Convert release date from RFC format to more readable
     * @return empty string if error else readable date eg., 16 September 2000, 16:00
     */
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

    /**
     * Convert duration of track into readable
     * @return String of times, 12:06:58
     */
    fun Long.inMillisToString(): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val calendar = Calendar.getInstance().apply { timeInMillis = this@inMillisToString }
        return sdf.format(calendar.time)
    }

    /**
     * Convert any number to current locale format
     * @return Formatted number with the currency, $12.99
     */
    fun Number.toMoneyFormat(): String = NumberFormat.getCurrencyInstance().format(this)
}