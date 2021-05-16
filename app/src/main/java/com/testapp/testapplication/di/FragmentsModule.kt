package com.testapp.testapplication.di

import com.testapp.testapplication.usersettings.UserSettingsFragment
import com.testapp.testapplication.authorization.AuthorizationFragment
import com.testapp.testapplication.registration.RegistrationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {
    @ContributesAndroidInjector
    abstract fun bindRegistrationFragment(): RegistrationFragment

    @ContributesAndroidInjector
    abstract fun bindAuthorizationFragment(): AuthorizationFragment

    @ContributesAndroidInjector
    abstract fun bindUserProfileFragment(): UserSettingsFragment
}