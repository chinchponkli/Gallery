package com.arjit.gallery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.arjit.gallery.GalleryApplication
import com.arjit.gallery.di.module.ActivityModule
import com.arjit.gallery.R
import com.arjit.gallery.databinding.FragmentGalleryBinding
import com.arjit.gallery.ui.viewmodel.GalleryViewModel
import javax.inject.Inject

class GalleryFragment : Fragment() {

    companion object {
        val TAG: String = GalleryFragment::class.java.simpleName
        private const val ARG_SCROLL_Y = "scroll_y"

        fun newInstance(): GalleryFragment = GalleryFragment()
    }

    @Inject lateinit var galleryViewModel: GalleryViewModel
    private lateinit var binding: FragmentGalleryBinding
    private var scrollPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GalleryApplication.applicationComponent!!.plus(ActivityModule(activity!!)).inject(this)
        lifecycle.addObserver(galleryViewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_gallery, container, false)
        binding.setLifecycleOwner(this)
        binding.viewModel = galleryViewModel
        setupRecyclerView()
        observeData()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.galleryGridView.layoutManager = GridLayoutManager(binding.galleryGridView.context, 2)
        binding.galleryGridView.adapter = galleryViewModel.adapter
    }

    private fun observeData() {
        galleryViewModel.livePullRequests.observe(this, Observer { newList ->
            galleryViewModel.adapter.submitList(newList)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scrollPosition = savedInstanceState?.getInt(ARG_SCROLL_Y) ?: 0
        binding.galleryGridView.smoothScrollBy(0, scrollPosition)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(ARG_SCROLL_Y, binding.galleryGridView.scrollY)
        binding.galleryGridView.scrollY
        super.onSaveInstanceState(outState)
    }
}
