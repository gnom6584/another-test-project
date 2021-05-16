package com.testapp.testapplication.utility.date

import com.testapp.testapplication.utility.date.DateConverter
import java.util.*

class DefaultDateConverter : DateConverter {

    override fun convertToString(date: Date) : String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return "${calendar.get(Calendar.DAY_OF_MONTH)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}"
    }
}