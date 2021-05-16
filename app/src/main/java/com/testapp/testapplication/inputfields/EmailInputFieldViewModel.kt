package com.testapp.testapplication.inputfields

import android.util.Patterns

class EmailInputFieldViewModel(initEmail: String = "") : NameInputFieldViewModel(initEmail) {

    override fun validate(): Boolean {
        val isEmailCorrect = Patterns.EMAIL_ADDRESS.matcher(text.get()!!).matches()
        if(!isEmailCorrect && !text.get()!!.isEmpty()) {
            validationError.set(ValidationError.Email)
            return false
        }
        else
            return super.validate()
    }
}