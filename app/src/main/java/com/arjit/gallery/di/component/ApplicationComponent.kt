package com.arjit.gallery.di.component

import com.arjit.gallery.di.module.ActivityModule
import com.arjit.gallery.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent {

    fun plus(activityModule: ActivityModule): ActivityComponent
}
