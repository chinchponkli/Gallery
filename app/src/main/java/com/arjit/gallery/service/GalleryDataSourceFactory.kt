package com.arjit.gallery.service

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arjit.gallery.ui.viewmodel.GalleryItemViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GalleryDataSourceFactory @Inject internal constructor(
    private val galleryService: GalleryService
) : DataSource.Factory<Int, GalleryItemViewModel>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var liveGalleryDataSource = MutableLiveData<GalleryDataSource>()

    override fun create(): DataSource<Int, GalleryItemViewModel> {
        val galleryDataSource = GalleryDataSource(galleryService, compositeDisposable)
        liveGalleryDataSource.postValue(galleryDataSource)
        return galleryDataSource
    }
}
