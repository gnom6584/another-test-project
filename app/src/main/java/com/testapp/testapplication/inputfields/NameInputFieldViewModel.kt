package com.testapp.testapplication.inputfields

import androidx.databinding.Observable
import androidx.databinding.ObservableField

open class NameInputFieldViewModel(initText: String = "") : ValidationInputFieldViewModel() {

    val text = ObservableField<String>(initText)

    override fun validate() : Boolean {

        val isEmpty = text.get()!!.isEmpty();

        validationError.set(
            if (isEmpty) ValidationError.Empty else ValidationError.None
        )

        return !isEmpty

    }

    init {
        text.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                validationError.set(ValidationError.None)
            }
        })
    }
}