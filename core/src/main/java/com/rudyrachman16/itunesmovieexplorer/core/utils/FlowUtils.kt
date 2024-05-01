package com.rudyrachman16.itunesmovieexplorer.core.utils

import com.rudyrachman16.itunesmovieexplorer.core.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object FlowUtils {
    fun <T> defaultFlowCallback(successCallback: suspend () -> T) = flow {
        emit(Status.Loading)
        try {
            emit(Status.Success(successCallback()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Status.Error(e))
        }
    }.cancellable().flowOn(Dispatchers.IO)
}