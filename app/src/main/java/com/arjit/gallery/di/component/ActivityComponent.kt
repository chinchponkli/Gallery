package com.arjit.gallery.di.component

import com.arjit.gallery.di.module.ActivityModule
import com.arjit.gallery.di.scope.PerActivity
import com.arjit.gallery.ui.activity.GalleryActivity
import com.arjit.gallery.ui.fragment.GalleryFragment
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(galleryActivity: GalleryActivity)

    fun inject(galleryFragment: GalleryFragment)
}
