package com.testapp.testapplication.authorization

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.testapp.testapplication.Navigator
import com.testapp.testapplication.UserProfile
import com.testapp.testapplication.utility.ToastGenerator
import com.testapp.testapplication.inputfields.EmailInputFieldViewModel
import com.testapp.testapplication.inputfields.PasswordInputFieldViewModel
import com.testapp.testapplication.rest.Backend
import com.testapp.testapplication.rest.CheckLoginData
import com.testapp.testapplication.rest.CheckLoginRequest
import com.testapp.testapplication.utility.ErrorToaster
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AuthorizationViewModel @Inject constructor(val backend: Backend, val userProfile: UserProfile, val navigator: Navigator, val toaster: ErrorToaster) : ViewModel(), CoroutineScope {

    val isLoading = ObservableBoolean(false)

    val email = EmailInputFieldViewModel()

    val password = PasswordInputFieldViewModel()

    val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    fun validate() : Boolean {
        val fields = arrayOf(email, password)
        return !fields.map { it.validate() }.any{ !it }
    }

    fun authorize() {
        val validateSuccess = validate()

        if(!validateSuccess)
            return

        isLoading.set(true)

        userProfile.email = email.text.get()!!
        userProfile.password = password.text.get()!!

        launch {

            val result = backend.checkLogin(CheckLoginRequest("raw", CheckLoginData(email.text.get()!!, password.text.get()!!)))
            withContext(Dispatchers.Main) {
                result.onSuccess {
                    navigator.navigateFromAuthorizationScreenToUserSettingScreen(null)
                }.except { error ->
                    toaster.pushAuthorizationError(error)
                }
            }
            isLoading.set(false)
        }
    }
}