package ernox.set

import android.databinding.BindingAdapter
import android.support.v7.widget.CardView
import android.view.animation.AnimationUtils
import android.widget.ImageView
import ernox.set.models.Card

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

        @JvmStatic
        @BindingAdapter("android:stateListAnimator")
        fun setCardAnimation(view: CardView, card: Card) {

            val context = view.context

            if(card.isHint)
            {
                val animShake = AnimationUtils.loadAnimation(context, R.anim.shake)

                view.startAnimation(animShake)
                // view.stateListAnimator = AnimatorInflater.loadStateListAnimator(context, R.animator.card_shake)
            }
//            else if(card.isSelected)
//                view.stateListAnimator = AnimatorInflater.loadStateListAnimator(context, R.animator.button_elevation)
//            else
//                view.stateListAnimator = AnimatorInflater.loadStateListAnimator(context, R.animator.button_drop)
        }
    }
}