package com.testapp.testapplication.di

import androidx.lifecycle.ViewModelProvider
import com.testapp.testapplication.Application
import com.testapp.testapplication.Navigator
import com.testapp.testapplication.UserProfile
import com.testapp.testapplication.rest.Backend
import com.testapp.testapplication.rest.di.RestModule
import com.testapp.testapplication.utility.ErrorToaster
import com.testapp.testapplication.utility.photo.PhotoUtility
import com.testapp.testapplication.utility.resources.ResourcesExtractor
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules =
[
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    FragmentsModule::class,
    ViewModelModule::class,
    RestModule::class,
    UtilityModule::class
])
interface ApplicationComponent : AndroidInjector<Application> {

    fun getViewModelFactory(): ViewModelProvider.Factory

    fun getBackend(): Backend

    fun getUserProfile(): UserProfile

    fun getNavigator(): Navigator

    fun getPhotoUtility(): PhotoUtility

    fun getResourcesExtractor(): ResourcesExtractor

    fun getErrorToaster(): ErrorToaster

    override fun inject(application: Application)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application) : Builder

        fun build(): ApplicationComponent
    }
}