package com.arjit.gallery.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.arjit.gallery.GalleryApplication
import com.arjit.gallery.databinding.ActivityGalleryBinding
import com.arjit.gallery.R
import com.arjit.gallery.di.module.ActivityModule
import com.arjit.gallery.ui.fragment.GalleryFragment

class GalleryActivity : AppCompatActivity() {

    private var pullRequestFragment: GalleryFragment? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GalleryApplication.applicationComponent!!
            .plus(ActivityModule(this)).inject(this)

        val binding = DataBindingUtil.setContentView<ActivityGalleryBinding>(this, R.layout.activity_gallery)
        setSupportActionBar(binding.toolbar)

        pullRequestFragment = supportFragmentManager.findFragmentByTag(GalleryFragment.TAG) as? GalleryFragment

        if (pullRequestFragment == null) {
            pullRequestFragment = GalleryFragment.newInstance()
            supportFragmentManager.beginTransaction().run {
                add(binding.fragmentContainer.id, pullRequestFragment!!, GalleryFragment.TAG)
                commit()
            }
        }
    }
}
