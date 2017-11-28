package ernox.set.adapters.holders

import android.content.Context
import android.graphics.Color
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

    fun bind(position: Int, card: Card) {

        val figureId = getImageId(card.figure.getDrawableName())

        addFiguresToCard(card.numberOfFigures, figureId)

        itemView.setOnClickListener {
            onItemClickedListener.onItemClicked(position, card, itemView)
        }
    }

    private fun getContext() : Context = itemView.context

    private fun getImageId(drawableName: String) : Int {
        val context = getContext()
        return context.resources.getIdentifier(drawableName, "drawable", context.packageName)
    }

    private fun addFiguresToCard(numberOfFigures: Short, figureId: Int) {

        val cardView = itemView.findViewById<LinearLayout>(R.id.card)

        cardView.removeAllViews()

        for(i in 1..numberOfFigures)
        {
            val imageView = ImageView(getContext())
            imageView.setImageResource(figureId)

            cardView.addView(imageView)
        }
    }
}