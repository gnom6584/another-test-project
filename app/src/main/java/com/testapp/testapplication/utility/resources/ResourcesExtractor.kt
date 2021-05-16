package com.testapp.testapplication.utility.resources

interface ResourcesExtractor {

    fun extractInteger(id: Int): Int

    fun extractString(id: Int): String
}