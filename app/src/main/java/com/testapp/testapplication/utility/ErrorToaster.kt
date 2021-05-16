package com.testapp.testapplication.utility

import com.testapp.testapplication.R
import com.testapp.testapplication.utility.resources.ResourcesExtractor

class ErrorToaster(val regErrorStr: String, val authErrorStr: String, val avatarUploadErrorStr: String, val updateProfileErrorStr: String) : ToastGenerator() {

    fun pushRegistrationError(errorDescription: String? = null) = push(buildMessage(regErrorStr, errorDescription))

    fun pushAuthorizationError(errorDescription: String? = null) = push(buildMessage(authErrorStr, errorDescription))

    fun pushAvatarUploadError(errorDescription: String? = null) = push(buildMessage(avatarUploadErrorStr, errorDescription))

    fun pushUpdateProfileError(errorDescription: String? = null) = push(buildMessage(updateProfileErrorStr, errorDescription))

    private fun buildMessage(msg: String, description: String?) = if(description == null) msg else "$msg:$description"
}