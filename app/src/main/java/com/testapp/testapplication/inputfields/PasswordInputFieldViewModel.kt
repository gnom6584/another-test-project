package com.testapp.testapplication.inputfields

import androidx.databinding.ObservableBoolean

open class PasswordInputFieldViewModel(val initInput: String = "") : NameInputFieldViewModel(initInput)
{
    val isShowingPassword = ObservableBoolean(false)

    fun switchShowPasswordState() {
        isShowingPassword.set(!isShowingPassword.get())
    }
}