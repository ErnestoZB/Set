package ernox.set.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ernox.set.R
import ernox.set.adapters.holders.CardViewHolder
import ernox.set.interfaces.OnItemClickedListener
import ernox.set.models.Card

/**
 * Created by Ernesto on 26/11/2017.
 */
class CardAdapter(private val items: ArrayList<Card?>,
                  private val onItemClickedListener: OnItemClickedListener<Card>) : RecyclerView.Adapter<CardViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.holder_card, parent, false)

        val height = parent.measuredHeight / 4
        v.minimumHeight = (height - 112 / parent.context.resources.displayMetrics.density).toInt()

        val width = parent.measuredWidth / 3
        v.minimumWidth = width

        return CardViewHolder(v, onItemClickedListener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(position, items[position])
    }

    override fun getItemCount(): Int = items.size
}