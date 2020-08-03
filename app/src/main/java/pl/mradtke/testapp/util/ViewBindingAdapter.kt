package pl.mradtke.testapp.util

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.mradtke.testapp.R

/**
 * @author Michał Radtke
 * @version 28.07.2020
 */

/**
 * Binding adapter to set the view's visibility using boolean value.
 * XML tag: "app:shouldBeVisibleOrGone".
 */
@BindingAdapter("shouldBeVisibleOrGone")
fun View.setVisibleOrGone(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

/**
 * Binding adapter to load image from URL.
 * XML tag: "app:imageUrl".
 */
@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    if (!TextUtils.isEmpty(url))
        loadImage(context, url)
    else
        setImageResource(R.drawable.ic_avatar_24)
}

/**
 * Binding adapter to binds list adapter to the recycler view.
 * XML tag: "app:adapter".
 */
@BindingAdapter("adapter")
fun RecyclerView.setRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
    this.run {
        setHasFixedSize(true)
        this.adapter = adapter
    }
}
