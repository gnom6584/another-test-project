package com.testapp.testapplication.utility.resources

import android.content.Context
import android.content.res.Resources

class ResourcesExtractorImpl(val resources: Resources) : ResourcesExtractor {

    override fun extractInteger(id: Int) = resources.getInteger(id)

    override fun extractString(id: Int) = resources.getString(id)

}