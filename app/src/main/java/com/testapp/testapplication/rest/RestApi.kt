package com.testapp.testapplication.rest

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class RegisterData(
    val email: String,
    val firstname: String,
    val lastname: String,
    val password: String
)

data class RegisterRequest(
    @SerializedName("mode")
    val mode: String,
    @SerializedName("raw")
    val raw: RegisterData)

data class CheckLoginData(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

data class CheckLoginRequest(
    @SerializedName("mode")
    val mode: String,
    @SerializedName("raw")
    val raw: CheckLoginData)

data class UpdateProfileData(
    @SerializedName("email")
    val email: String,
    @SerializedName("firstname")
    val name: String,
    @SerializedName("lastname")
    val surname: String,
    @SerializedName("birthdate")
    val birthDate: String,
    @SerializedName("preferences")
    val preferences: List<String>,
    @SerializedName("organization")
    val organization: String,
    @SerializedName("position")
    val workingPosition: String,
    @SerializedName("birth_place")
    val birthPlace: String,
    @SerializedName("middlename")
    val patronymic: String,
    @SerializedName("id")
    val id: String
)

data class UpdateProfileRequest(
    @SerializedName("mode")
    val mode: String,
    @SerializedName("raw")
    val raw: UpdateProfileData
)

data class UploadAvatarResponse(
    @SerializedName("filename")
    val id: String)

interface RestApi  {
    @POST("/registerUser")
    suspend fun registerUser(@Body body: RegisterRequest) : Response<Void>

    @POST("/checkLogin")
    suspend fun checkLogin(@Body body: CheckLoginRequest) : Response<Void>

    @POST("/updateProfile")
    suspend fun updateProfile(@Body body: UpdateProfileRequest) : Response<Void>

    @Multipart
    @POST("/uploadAvatar")
    suspend fun uploadAvatar(@Part file: MultipartBody.Part): Response<UploadAvatarResponse>
}