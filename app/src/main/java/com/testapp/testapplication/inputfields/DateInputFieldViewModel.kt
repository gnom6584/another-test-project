package com.testapp.testapplication.inputfields

import androidx.databinding.ObservableField
import com.testapp.testapplication.OnDateChangedListener
import java.util.*

class DateInputFieldViewModel(initDate: Date? = null) : ValidationInputFieldViewModel(), OnDateChangedListener {

    val date = ObservableField<Date?>(initDate)

    override fun validate(): Boolean {
        val isEmpty = date.get() == null

        if(isEmpty)
            validationError.set(ValidationError.Empty)

        return !isEmpty
    }

    override fun onDateChanged(date: Date) {
        this.date.set(date)
        validationError.set(ValidationError.None)
    }

}