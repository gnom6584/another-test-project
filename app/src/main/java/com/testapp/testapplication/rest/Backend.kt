package com.testapp.testapplication.rest

import okhttp3.MultipartBody

interface Backend {

    class ResultValue<T> {

        companion object {

            fun<T> success(value: T) = ResultValue(value)

            fun<T> error(errorMsg: String?) = ResultValue<T>(errorMsg)
        }

        private var value: T? = null

        private var errorMsg: String? = null

        private constructor(errorMsg: String?) {
            this.errorMsg = errorMsg
        }

        private constructor(value: T) {
            this.value = value
        }

        fun onSuccess(resultReceiver: (T) -> Unit) = this.apply {
            value?.let(resultReceiver)
        }

        fun except(errorReceiver: (String?) -> Unit) = this.apply {
            if(value == null)
                errorReceiver.invoke(errorMsg)
        }
    }

    class Result {

        companion object {

            fun success() = Result()

            fun error(errorMsg: String?) = Result(errorMsg)
        }

        private var success: Boolean = false

        private var errorMsg: String? = null

        private constructor() {
            success = true
        }

        private constructor(errorMsg: String?) {
            this.errorMsg = errorMsg
        }

        fun onSuccess(resultReceiver: () -> Unit) = this.apply{
            if(success) {
                resultReceiver.invoke()
            }
        }

        fun except(errorReceiver: (String?) -> Unit) = this.apply{
            if(!success)
                errorReceiver.invoke(errorMsg)
        }
    }

    suspend fun registerUser(body: RegisterRequest) : Result

    suspend fun checkLogin(body: CheckLoginRequest) : Result

    suspend fun updateProfile(body: UpdateProfileRequest): Result

    suspend fun uploadAvatar(file: MultipartBody.Part): ResultValue<UploadAvatarResponse>
}