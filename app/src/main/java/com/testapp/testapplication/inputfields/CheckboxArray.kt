package com.testapp.testapplication.inputfields

abstract class CheckboxArray(initCheckboxes: List<Boolean>) : ValidationInputFieldViewModel() {
    val checkboxes = initCheckboxes.map { CheckBoxInputFieldViewModel(it) }
}