package com.arjit.gallery.ui.viewmodel

import com.arjit.gallery.model.Image

class GalleryItemViewModel(val image: Image) : ListItemViewModel<Image>() {

    override val id: String = GalleryItemViewModel::class.java.simpleName

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (javaClass != other?.javaClass) return false

        return id == (other as GalleryItemViewModel).id
    }

    override fun hashCode(): Int = id.hashCode()
}
