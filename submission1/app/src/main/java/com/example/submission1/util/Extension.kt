package com.example.submission1.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("loadImage")
fun ImageView.loadUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .thumbnail(0.3f)
        .placeholder(ColorDrawable(Color.LTGRAY))
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(RequestOptions.fitCenterTransform())
        .into(this)
}

@BindingAdapter("loadImagePotrait")
fun loadPotraitImage(view: ImageView, imageUrl: String?) {
    if (imageUrl.isNullOrEmpty()) return

    Glide.with(view.context)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .thumbnail(0.2f)
        .placeholder(ColorDrawable(Color.LTGRAY))
        .into(view)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}