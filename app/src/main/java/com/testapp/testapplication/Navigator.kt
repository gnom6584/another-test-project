package com.testapp.testapplication

import android.os.Bundle
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {

    interface Listener {
        fun receiveNavigationAction(action: Int, bundle: Bundle?)
    }

    init {
        Log.d("TAG", "navigator created")
    }

    var navigationListener : Listener? = null

    fun navigateFromRegistrationScreenToAuthorizationScreen(bundle: Bundle?) =
        navigationListener?.receiveNavigationAction(R.id.action_registrationScreen_to_authorizationScreen, bundle)

    fun navigateFromAuthorizationScreenToUserSettingScreen(bundle: Bundle?) =
        navigationListener?.receiveNavigationAction(R.id.action_authorizationScreen_to_userSettingsScreen, bundle)
}