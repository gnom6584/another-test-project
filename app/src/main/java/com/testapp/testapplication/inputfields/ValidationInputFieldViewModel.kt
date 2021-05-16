package com.testapp.testapplication.inputfields

import androidx.databinding.Observable
import androidx.databinding.ObservableField

abstract class ValidationInputFieldViewModel {

    val validationError = ObservableField<ValidationError>(ValidationError.None)

    abstract fun validate() : Boolean
}