package com.testapp.testapplication.rest.di

import com.testapp.testapplication.rest.Backend
import com.testapp.testapplication.rest.RestApi
import com.testapp.testapplication.rest.RestApiBackend
import com.testapp.testapplication.rest.RestApiMock
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RestModule {

    @Provides
    fun getUrl() = "http://94.127.67.113:8099"

    @Singleton
    @Provides
    fun getConverterFactory() = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun getHttpBuilder() = OkHttpClient.Builder()

    @Singleton
    @Provides
    fun getLoggingInterceptor() = HttpLoggingInterceptor()

    @Singleton
    @Provides
    fun getHttpClient(builder: OkHttpClient.Builder, interceptor: HttpLoggingInterceptor) =
        builder.addInterceptor(interceptor).build().apply {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }

    @Singleton
    @Provides
    fun getRetrofit(url: String, gsonConverterFactory: GsonConverterFactory, httpClient: OkHttpClient) =
        Retrofit.Builder().baseUrl(getUrl()).addConverterFactory(gsonConverterFactory).client(httpClient).build()

    @Singleton
    @Provides
    fun provideBackend(retrofit: Retrofit) : Backend = RestApiBackend(retrofit.create(RestApi::class.java))
}