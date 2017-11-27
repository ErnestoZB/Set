package ernox.set.adapters.holders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import ernox.set.R
import ernox.set.models.Card


/**
 * Created by Ernesto on 26/11/2017.
 */
class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(card: Card) {

        val figureId = getImageId(card.figure.getDrawableName())

        addFiguresToCard(card.numberOfFigures, figureId)
    }

    private fun getContext() : Context = itemView.context

    private fun getImageId(drawableName: String) : Int {
        val context = getContext()
        return context.resources.getIdentifier(drawableName, "drawable", context.packageName)
    }

    private fun addFiguresToCard(numberOfFigures: Short, figureId: Int) {

        for(i in 1..numberOfFigures)
        {
            val imageView = ImageView(getContext())
            imageView.setImageResource(figureId)

            itemView.findViewById<LinearLayout>(R.id.card).addView(imageView)
        }
    }
}