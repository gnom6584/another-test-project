package com.testapp.testapplication.di

import android.content.Context
import android.content.res.Resources
import com.testapp.testapplication.Application
import com.testapp.testapplication.R
import com.testapp.testapplication.utility.ErrorToaster
import com.testapp.testapplication.utility.photo.PhotoUtility
import com.testapp.testapplication.utility.photo.PhotoUtilityImpl
import com.testapp.testapplication.utility.resources.ResourcesExtractor
import com.testapp.testapplication.utility.resources.ResourcesExtractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilityModule {

    @Provides
    fun getContext(application: Application) = application.applicationContext

    @Provides
    fun getResources(context: Context) = context.resources

    @Singleton
    @Provides
    fun providePhotoUtility(context: Context) : PhotoUtility = PhotoUtilityImpl(context)

    @Singleton
    @Provides
    fun provideResourcesExtractor(resources: Resources): ResourcesExtractor = ResourcesExtractorImpl(resources)

    @Singleton
    @Provides
    fun provideToaster(resourcesExtractor: ResourcesExtractor) = ErrorToaster(
        resourcesExtractor.extractString(R.string.registration_error),
        resourcesExtractor.extractString(R.string.authorization_error),
        resourcesExtractor.extractString(R.string.avatar_upload_error),
        resourcesExtractor.extractString(R.string.update_profile_error)
    )
}