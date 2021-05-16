package com.testapp.testapplication.rest

import okhttp3.MultipartBody
import retrofit2.Response

class RestApiBackend(val restApi: RestApi) : Backend {
    override suspend fun registerUser(body: RegisterRequest) = restApi.registerUser(body).run {
        if(isSuccessful && code() == 200)
            return@run Backend.Result.success()
        Backend.Result.error(errorBody()?.string())
    }

    override suspend fun checkLogin(body: CheckLoginRequest) = restApi.checkLogin(body).run {
        if(isSuccessful && code() == 200)
            return@run Backend.Result.success()
        Backend.Result.error(errorBody()?.string())
    }

    override suspend fun updateProfile(body: UpdateProfileRequest) = restApi.updateProfile(body).run {
        if(isSuccessful && code() == 200)
            return@run Backend.Result.success()
        Backend.Result.error(errorBody()?.string())
    }

    override suspend fun uploadAvatar(file: MultipartBody.Part) = restApi.uploadAvatar(file).run {
        if(isSuccessful && code() == 200)
            return@run Backend.ResultValue.success(body()!!)
        Backend.ResultValue.error<UploadAvatarResponse>(errorBody()?.string())
    }

}