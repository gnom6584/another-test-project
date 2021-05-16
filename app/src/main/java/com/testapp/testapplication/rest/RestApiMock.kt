package com.testapp.testapplication.rest

import android.util.Log
import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import retrofit2.Response
import kotlin.random.Random

class RestApiMock : Backend {
    override suspend fun registerUser(body: RegisterRequest): Backend.Result {
        delay(Random.nextLong(3000))
        return Backend.Result.success()
    }

    override suspend fun checkLogin(body: CheckLoginRequest): Backend.Result {
        delay(Random.nextLong(3000))
        return Backend.Result.success()
    }

    override suspend fun updateProfile(body: UpdateProfileRequest): Backend.Result {
        delay(Random.nextLong(3000))
        return Backend.Result.success()
    }

    override suspend fun uploadAvatar(file: MultipartBody.Part): Backend.ResultValue<UploadAvatarResponse> {
        delay(Random.nextLong(3000))
        return Backend.ResultValue.success(UploadAvatarResponse("0"))
    }

}