package com.arjit.gallery.service

import android.content.Context
import android.os.Environment
import com.arjit.gallery.di.qualifier.ApplicationContext
import com.arjit.gallery.model.Image
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GalleryService @Inject internal constructor(@ApplicationContext context: Context) {

    private val imageFiles = mutableListOf<Image>()

    companion object {
        private const val PAGE_SIZE = 10
    }

    init {
       fetchImageList(context)
    }

    fun getImages(page: Int): Observable<Image> =
        Observable.fromIterable(
            imageFiles.asSequence()
                .drop((page - 1) * 10)
                .take(PAGE_SIZE)
                .toList()
        ).subscribeOn(Schedulers.io())

    private fun fetchImageList(context: Context) {
        val dir = File("/storage/emulated/0/DCIM/Camera")
        dir?.run {
            dir.listFiles()?.forEach { file -> imageFiles.add(Image(file.name, file.absolutePath)) }
        }
    }
}
