package com.arjit.gallery.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import com.arjit.gallery.di.scope.PerActivity
import com.arjit.gallery.R
import com.arjit.gallery.databinding.ItemGalleryBinding
import com.arjit.gallery.model.Image
import com.arjit.gallery.service.GalleryDataSource
import com.arjit.gallery.service.GalleryDataSourceFactory
import com.arjit.gallery.ui.adapter.BindingViewHolder
import com.arjit.gallery.ui.adapter.GenericPagedAdapter
import javax.inject.Inject

@PerActivity
class GalleryViewModel @Inject internal constructor(
    galleryDataSourceFactory: GalleryDataSourceFactory
) : ListViewModel<GalleryItemViewModel, Image>() {

    companion object {
        private const val PAGE_SIZE = 10
        private const val ENABLE_PAGE_PACEHOLDERS = false
    }

    var livePullRequests: LiveData<PagedList<GalleryItemViewModel>>

    init {
        val pagedListConfig =
            PagedList.Config.Builder()
                .setEnablePlaceholders(ENABLE_PAGE_PACEHOLDERS)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .build()

        livePullRequests = LivePagedListBuilder(galleryDataSourceFactory, pagedListConfig).build()

        isLoading = Transformations
            .switchMap(galleryDataSourceFactory.liveGalleryDataSource, GalleryDataSource::isLoading)
    }

    val adapter = Adapter(diffUtilItemCallback)

    class Adapter(itemCallback: DiffUtil.ItemCallback<GalleryItemViewModel>) :
        GenericPagedAdapter<GalleryItemViewModel, BindingViewHolder<ItemGalleryBinding>,
                ItemGalleryBinding, Image>(itemCallback, R.layout.item_gallery)
}
