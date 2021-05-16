package com.testapp.testapplication

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.testapp.testapplication.di.DaggerApplicationComponent
import com.testapp.testapplication.di.UtilityModule
import com.testapp.testapplication.di.ViewModelFactory
import com.testapp.testapplication.rest.Backend
import com.testapp.testapplication.rest.RestApiMock
import com.testapp.testapplication.rest.di.RestModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class Application : Application(), HasActivityInjector, HasSupportFragmentInjector {

    companion object {
    }

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector() = activityInjector

    override fun supportFragmentInjector() = fragmentInjector

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }
}