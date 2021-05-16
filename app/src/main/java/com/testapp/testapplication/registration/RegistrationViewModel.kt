package com.testapp.testapplication.registration

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.testapp.testapplication.Navigator
import com.testapp.testapplication.UserProfile
import com.testapp.testapplication.utility.ToastGenerator
import com.testapp.testapplication.inputfields.*
import com.testapp.testapplication.rest.Backend
import com.testapp.testapplication.rest.RegisterData
import com.testapp.testapplication.rest.RegisterRequest
import com.testapp.testapplication.utility.ErrorToaster
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RegistrationViewModel @Inject constructor(val backend: Backend, val navigator: Navigator, val userProfile: UserProfile, val toaster: ErrorToaster) : ViewModel(), CoroutineScope {

    val surname = NameInputFieldViewModel(userProfile.lastName)

    val name = NameInputFieldViewModel(userProfile.firstName)

    val patronymic = NameInputFieldViewModel(userProfile.middleName)

    val email = EmailInputFieldViewModel(userProfile.email)

    val password = PasswordInputFieldViewModel(userProfile.password)

    val repeatPassword = RepeatPasswordInputFieldViewModel(password)

    val isLoading = ObservableBoolean(false)

    val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    fun validate() : Boolean {
        val fields = arrayOf(surname, name, patronymic, email, password, repeatPassword)
        return !fields.map { it.validate() }.any{ !it }
    }

    fun tryRegister() {

        val validationSuccess = validate()

        if(!validationSuccess)
            return

        isLoading.set(true)

//        userProfile.apply {
//            firstName = name.text.get()!!
//            lastName = surname.text.get()!!
//            middleName = patronymic.text.get()!!
//            email = this@RegistrationViewModel.email.text.get()!!
//            password = this@RegistrationViewModel.password.text.get()!!
//        }

        launch {
            val registerData = RegisterData(email.text.get()!!, name.text.get()!!, surname.text.get()!!, password.text.get()!!)
            val result = backend.registerUser(RegisterRequest("raw", registerData))
            withContext(Dispatchers.Main) {
                result.onSuccess {
                    navigator.navigateFromRegistrationScreenToAuthorizationScreen(null)
                }.except { error ->
                    toaster.pushRegistrationError(error)
                }
            }
            isLoading.set(false)
        }

//        val serivce = Retrofit.Builder()
//            .baseUrl("http://94.127.67.113:8099/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build().create(RestApi::class.java)

    }
}