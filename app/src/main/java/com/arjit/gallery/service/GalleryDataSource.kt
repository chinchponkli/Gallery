package com.arjit.gallery.service

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.arjit.gallery.ui.viewmodel.GalleryItemViewModel
import io.reactivex.disposables.CompositeDisposable

class GalleryDataSource(
    private val galleryService: GalleryService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, GalleryItemViewModel>() {

    private var sourceIndex: Int = 1
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GalleryItemViewModel>) {
        Log.d("Loading Page: ", "$sourceIndex")
        galleryService.getImages(1)
            .map { image -> GalleryItemViewModel(image) }
            .toList()
            .doOnSubscribe { disposable ->
                compositeDisposable.add(disposable)
                isLoading.postValue(true)
            }
            .subscribe(
                { pullRequestItems ->
                    Log.d("Loaded Page: ", "$sourceIndex")
                    isLoading.postValue(false)
                    sourceIndex += 1
                    callback.onResult(pullRequestItems, null, sourceIndex)
                },
                { throwable ->
                    Log.d("Failed to Load Page: ", "$throwable")
                    isLoading.postValue(false)
                }
            )
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GalleryItemViewModel>) {
        Log.d("Loading Page: ", "$sourceIndex")
        galleryService.getImages(sourceIndex)
            .map { image -> GalleryItemViewModel(image) }
            .toList()
            .doOnSubscribe { disposable ->
                compositeDisposable.add(disposable)
                isLoading.postValue(true)
            }
            .subscribe(
                { pullRequestItems ->
                    Log.d("Loaded Page: ", "$sourceIndex")
                    isLoading.postValue(false)
                    sourceIndex += 1
                    callback.onResult(pullRequestItems, sourceIndex)
                },
                { throwable ->
                    Log.d("Failed to Load Page: ", "$throwable")
                    isLoading.postValue(false)
                }
            )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GalleryItemViewModel>) {
        // not going to do anything here
    }
}
