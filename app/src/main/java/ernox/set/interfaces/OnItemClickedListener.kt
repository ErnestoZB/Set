package ernox.set.interfaces

import android.view.View

/**
 * Created by Ernesto on 26/11/2017.
 */
interface OnItemClickedListener<in T> {
    fun onItemClicked(position: Int, item: T, view: View)
}