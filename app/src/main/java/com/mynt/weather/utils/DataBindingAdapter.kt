package com.mynt.weather.utils

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.mynt.weather.R


@SuppressLint("CheckResult")
@BindingAdapter(
    value = ["imageUrl", "placeHolderResource"],
    requireAll = false
)
fun loadImage(
    imageView: ImageView,
    imageUrl: String?,
    placeHolderResource: Int = R.drawable.default_image_bg,
) {
    val imageUrlFinal = UrlUtils.getImageUrl(imageUrl ?: "01d")
    val requestOptions = RequestOptions.placeholderOf(placeHolderResource)

    if (imageUrl?.isNotBlank() == true) {
        Glide.with(imageView.context).setDefaultRequestOptions(requestOptions).load(imageUrlFinal)
            .into(imageView)
    } else {
        Glide.with(imageView.context).setDefaultRequestOptions(requestOptions)
            .load(R.drawable.transparent_png)
            .into(imageView)
    }
}