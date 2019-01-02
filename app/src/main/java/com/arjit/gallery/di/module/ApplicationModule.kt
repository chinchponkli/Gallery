package com.arjit.gallery.di.module

import android.app.Application
import android.content.Context
import com.arjit.gallery.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @ApplicationContext
    fun provideApplicationContext(): Context = application
}
