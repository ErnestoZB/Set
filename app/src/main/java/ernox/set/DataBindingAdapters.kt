package ernox.set

import android.databinding.BindingAdapter
import android.widget.ImageView

/**
 * Created by Ernesto on 24/12/2017.
 */
class DataBindingAdapters {

    companion object {
        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageUri(view: ImageView, imageName: String?) {

            val context = view.context

            imageName?.let {
                val resID = context.resources.getIdentifier(it, "drawable", context.packageName)
                view.setImageResource(resID)
            }
        }
    }
}