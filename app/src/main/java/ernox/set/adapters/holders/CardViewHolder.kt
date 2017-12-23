package ernox.set.adapters.holders

import android.animation.AnimatorInflater
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import ernox.set.R
import ernox.set.interfaces.OnItemClickedListener
import ernox.set.models.Card


/**
 * Created by Ernesto on 26/11/2017.
 */
class CardViewHolder(itemView: View,
                     private val onItemClickedListener: OnItemClickedListener<Card>) : RecyclerView.ViewHolder(itemView) {

    private fun getContext() : Context = itemView.context

    fun bind(position: Int, card: Card?) {

        if(card == null) {
            hideCard()
            return
        }

        if(!card.isSelected) {
            deselectCard()
        }

        val figureId = getImageId(card.figure.getDrawableName())

        addFiguresToCard(card.numberOfFigures, figureId)

        card.takeIf { card.isHint }?.apply { showAsHint() }

        itemView.setOnClickListener {
            onItemClickedListener.onItemClicked(position, card, itemView)
        }
    }

    private fun deselectCard() {
        (itemView as CardView).setCardBackgroundColor(Color.WHITE)
    }

    private fun hideCard() {
        itemView.visibility = View.GONE
    }

    private fun showAsHint() {
        val elevateCard = AnimatorInflater.loadStateListAnimator(getContext(), R.animator.button_elevation)
        itemView.stateListAnimator = elevateCard
    }

    private fun getImageId(drawableName: String) : Int {
        val context = getContext()
        return context.resources.getIdentifier(drawableName, "drawable", context.packageName)
    }

    private fun addFiguresToCard(numberOfFigures: Short, figureId: Int) {

        val cardView = itemView.findViewById<LinearLayout>(R.id.card)

        cardView.removeAllViews()

        for(i in 1..numberOfFigures) {
            val imageView = createFigureImage(figureId)
            cardView.addView(imageView)
        }
    }

    private fun createFigureImage(figureId: Int) : ImageView {

        val imageView = ImageView(getContext())
        imageView.setImageResource(figureId)

        val sideSize = getFigureMinSize()
        imageView.minimumWidth = sideSize
        imageView.minimumHeight = sideSize

        return imageView
    }

    private fun getFigureMinSize() : Int {

        val maxHeight = itemView.minimumHeight / 3

        return (maxHeight - 19 / getContext().resources.displayMetrics.density).toInt()
    }
}