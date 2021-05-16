package com.testapp.testapplication.inputfields

import androidx.databinding.Observable

class AnySelectedCheckboxArray(initCheckboxes: List<Boolean>) : CheckboxArray(initCheckboxes) {

    init {
        checkboxes.forEach { field ->
            field.state.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    validationError.set(ValidationError.None)
                }
            })
        }
    }

    override fun validate(): Boolean {
        val isAnySelected = checkboxes.any { it.state.get() }

        if(!isAnySelected)
            validationError.set(ValidationError.NoVariantsSelected)

        return isAnySelected
    }
}