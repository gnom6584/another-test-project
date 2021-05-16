package com.testapp.testapplication

import android.util.Log
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProfile @Inject constructor() {

    var lastName = ""

    var firstName = ""

    var middleName = ""

    var email = ""

    var password = ""

    var birthPlace = ""

    var birthDate: Date? = null

    var organization = ""

    var workPosition = ""

    var preferences = listOf<String>()
}