package com.rudyrachman16.itunesmovieexplorer.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.rudyrachman16.itunesmovieexplorer.R

object ImageUtils {
    /**
     * Load an online image (PNG or JPG) using an image url, into the ImageView
     * @see ImageView
     * @see Glide
     * @param imageUrl url of the image
     */
    fun ImageView.load(imageUrl: String) {
        Glide.with(this).load(imageUrl).apply(
            RequestOptions.placeholderOf(loadingDrawable(this.context)).error(
                R.drawable.ic_broken_image
            )
        ).into(this)
    }

    /**
     * Load an online SVG image using an image url, into the ImageView
     * @see ImageView
     * @see GlideToVectorYou
     * @param imageUrl url of the image
     */
    fun ImageView.loadSVG(imageUrl: String) {
        GlideToVectorYou.init().with(this.context)
            .setPlaceHolder(R.drawable.ic_downloading, R.drawable.ic_broken_image).load(
            Uri.parse(imageUrl), this
        )
    }

    /**
     * Loading circular drawable for loading placeholder when ImageView.load()
     * @see ImageView.load
     * @param context an Activity Context
     * @return animated circular for loading indicator
     */
    private fun loadingDrawable(context: Context) = CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 20f
        start()
    }
}