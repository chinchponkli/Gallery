package com.arjit.gallery

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.arjit.gallery.di.component.ApplicationComponent
import com.arjit.gallery.di.module.ApplicationModule
import com.arjit.gallery.di.component.DaggerApplicationComponent

class GalleryApplication : MultiDexApplication() {

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }

        @JvmStatic
        var applicationComponent: ApplicationComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        ApplicationModule(this).run {
            applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(this)
                .build()
        }
    }
}
