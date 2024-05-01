package com.rudyrachman16.itunesmovieexplorer.core

sealed class Status<out T> {
    data class Success<out T>(val data: T) : Status<T>()
    object Loading: Status<Nothing>()
    data class Error(val error: Exception): Status<Nothing>()
}
