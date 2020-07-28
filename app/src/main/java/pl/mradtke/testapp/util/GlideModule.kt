package pl.mradtke.testapp.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * @version 28.07.2020
 * @author Michał Radtke
 */

/**
 * App glide module. Put here Glide configuration if needed.
 */
@GlideModule
class GlideModule : AppGlideModule()

/**
 * Load image into ImageView using Glide.
 */
fun ImageView.loadImage(context: Context, url: String?) {
    Glide.with(context)
            .load(url)
            .centerCrop()
            .into(this)
}
