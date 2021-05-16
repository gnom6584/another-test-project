package com.testapp.testapplication.utility

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow

open class ToastGenerator {

    val listener: LiveData<String?> get() = mListener

    private val mListener = MutableLiveData<String?>()

    fun push(text: String) {
        mListener.value = text
        mListener.value = null
    }
}