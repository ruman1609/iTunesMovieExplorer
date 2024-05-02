package com.rudyrachman16.itunesmovieexplorer.utils

import android.content.Context
import android.widget.Toast

object UnitAndViewUtils {

    /**
     * Showing toast by extending the Activity Context
     * @param message message of the Toast
     * @param duration duration from Toast, make sure it only pass Toast.LENGTH_SHORT and Toast.LENGTH_LONG
     * @see Toast
     * @see Toast.LENGTH_LONG
     * @see Toast.LENGTH_SHORT
     */
    fun Context.makeToast(message: String?, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }
}