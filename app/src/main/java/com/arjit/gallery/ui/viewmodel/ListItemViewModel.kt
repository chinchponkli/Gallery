package com.arjit.gallery.ui.viewmodel

import androidx.recyclerview.widget.DiffUtil
import com.arjit.gallery.model.DiffModel

abstract class ListItemViewModel<T : DiffModel> : LifeCycleAwareViewModel() {
    abstract val id: String

    open val diffItemCallback: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem._id == id
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }
}
