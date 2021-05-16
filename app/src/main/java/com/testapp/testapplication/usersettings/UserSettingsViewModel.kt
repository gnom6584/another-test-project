package com.testapp.testapplication.usersettings

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.testapp.testapplication.Preference
import com.testapp.testapplication.R
import com.testapp.testapplication.UserProfile
import com.testapp.testapplication.inputfields.*
import com.testapp.testapplication.rest.Backend
import com.testapp.testapplication.rest.UpdateProfileData
import com.testapp.testapplication.rest.UpdateProfileRequest
import com.testapp.testapplication.utility.ErrorToaster
import com.testapp.testapplication.utility.photo.PhotoUtility
import com.testapp.testapplication.utility.date.DefaultDateConverter
import com.testapp.testapplication.utility.resources.ResourcesExtractor
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class UserSettingsViewModel
@Inject constructor(val backend: Backend, val userProfile: UserProfile, val photoUtility: PhotoUtility, val resourcesExtractor: ResourcesExtractor, val toaster: ErrorToaster)
    : ViewModel(), CoroutineScope {

    val surname = NameInputFieldViewModel(userProfile.lastName)

    val name = NameInputFieldViewModel(userProfile.firstName)

    val patronymic = NameInputFieldViewModel(userProfile.middleName)

    val birthPlace = NameInputFieldViewModel()

    val birthDate = DateInputFieldViewModel()

    val organization = NameInputFieldViewModel()

    val workingPosition = NameInputFieldViewModel()

    val preferences = AnySelectedCheckboxArray(Preference.values().map { false })

    val photo = ObservableField<Bitmap>()

    lateinit var photoFile: File

    lateinit var cameraPhotoUri: Uri
        private set

    private var avatarId: String = ""

    val isLoading = ObservableBoolean(false)

    val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    fun uploadAvatarByCamera() {
        uploadAvatar(cameraPhotoUri)
    }

    fun uploadAvatarByGallery(uri: Uri) {
        uploadAvatar(uri)
    }

    private fun uploadAvatar(uri: Uri) {
        isLoading.set(true)

        photo.set(null)

        val scaledBitmap = with(photoUtility) {
            val size = resourcesExtractor.extractInteger(R.integer.photo_bitmap_size)
            val sqrBmp = toSquarePhoto(getPhotoByUri(uri))
            val rtBmp = rotatePhoto(sqrBmp, getPhotoRotation(uri))
            getResizedPhoto(rtBmp, size, size)
        }

        val file = photoUtility.savePhotoAsJpg(scaledBitmap, "TEMPIMG.jpg")

        launch(Dispatchers.IO) {
            val body = MultipartBody.Part.createFormData(
                    "file", file.name, RequestBody.create(
                    MediaType.parse("multipart/form-data"), file
            ))

            val result = backend.uploadAvatar(body)

            withContext(Dispatchers.Main) {
                result.onSuccess { response ->
                    avatarId = response.id
                    photo.set(scaledBitmap)
                }.except { error ->
                    toaster.pushAvatarUploadError(error)
                }
            }
            isLoading.set(false)
        }
    }

    fun updateProfile() {

        val isValidationSuccess = kotlin.run {
            val preferencesValidation = preferences.validate()
            val fieldsValidation = arrayOf(surname, name, birthPlace, birthDate).map { it.validate() }.all { it }
            preferencesValidation && fieldsValidation
        }

        if (!isValidationSuccess)
            return

        isLoading.set(true)

        launch {
            val result = backend.updateProfile(UpdateProfileRequest("raw", UpdateProfileData(
                    email = userProfile.email,
                    surname = surname.text.get()!!,
                    patronymic = surname.text.get()!!,
                    name = name.text.get()!!,
                    birthDate = DefaultDateConverter().convertToString(birthDate.date.get()!!),
                    preferences = preferences.checkboxes.mapIndexed { index, checkBoxInputFieldViewModel ->
                        Preference.values()[index] to checkBoxInputFieldViewModel.state.get()
                    }.filter { it.second }.map { it.first }.map { if(it == Preference.ComputerGames) "computer games" else it.toString().toLowerCase(Locale.ROOT) },
                    organization = organization.text.get()!!,
                    workingPosition = workingPosition.text.get()!!,
                    birthPlace = birthPlace.text.get()!!,
                    id = avatarId
            )))
            withContext(Dispatchers.Main) {
                result.except { error ->
                    toaster.pushUpdateProfileError(error)
                }.onSuccess {
                    toaster.push("ВСЁ")
                }
                isLoading.set(false)
            }
        }
    }

    fun requestPhotoFileInitialization(context: Context) {
        if (!::photoFile.isInitialized)
            photoFile = File.createTempFile(
                    "IMG_",
                    ".jpg",
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            )

        if (!::cameraPhotoUri.isInitialized)
            cameraPhotoUri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    photoFile)
    }

    override fun onCleared() {
        super.onCleared()
        if (::photoFile.isInitialized)
            photoFile.delete()
    }
}