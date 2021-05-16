package com.testapp.testapplication.inputfields

import androidx.databinding.ObservableBoolean

class CheckBoxInputFieldViewModel(val initState: Boolean = false) {

    val state = ObservableBoolean(initState)

    fun switchState() {
        state.set(!state.get())
    }
}