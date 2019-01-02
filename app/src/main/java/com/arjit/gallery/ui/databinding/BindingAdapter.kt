package com.arjit.gallery.ui.databinding

import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.arjit.gallery.util.BasicImageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("imageSrc", "placeholder")
    fun setImageViewFromUrl(
        imageView: ImageView, imageUrl: String,
        placeholder: Drawable
    ) {

       /* Glide.with(imageView.context.applicationContext)
            .load(Uri.fromFile(File(imageUrl)))
            .apply(RequestOptions.errorOf(placeholder))
            .apply(RequestOptions().centerCrop())
            .apply(RequestOptions().override(600, 600))
            .into(imageView)*/

        imageView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View?) {
                BasicImageLoader.removeDecodeTask(File(imageUrl))
            }

            override fun onViewAttachedToWindow(v: View?) {
                BasicImageLoader.readFromDiskAsync(File(imageUrl), imageView.context.applicationContext, object : BasicImageLoader.OnImageReadListener {
                    override fun onImageRead(bitmap: Bitmap?) {
                        imageView.setImageBitmap(bitmap)
                    }

                    override fun onReadFailed() {
                        imageView.setImageDrawable(placeholder)
                    }

                })
            }
        })
    }

    @JvmStatic
    @BindingAdapter("bgTint")
    fun tintBackground(view: View, color: Int) {
        view.background?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }

    @JvmStatic
    @BindingAdapter("animateLoading")
    fun animateLoading(view: View, isLoading: Boolean) {
        if (isLoading) {
            view.animate().translationY(0f).setDuration(400).start()
        } else {
            view.animate().translationY(200f).setDuration(400).start()
        }
    }
}
