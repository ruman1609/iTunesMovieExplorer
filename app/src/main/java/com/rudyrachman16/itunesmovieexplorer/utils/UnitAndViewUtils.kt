package com.rudyrachman16.itunesmovieexplorer.utils

import android.content.Context
import android.widget.Toast

object UnitAndViewUtils {
    fun Int.dpToPx(context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return (this * scale + .5f).toInt()
    }

    fun Context.makeToast(message: String?, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }
}