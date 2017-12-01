package ernox.set.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ernox.set.R
import ernox.set.adapters.holders.HighScoreViewHolder
import ernox.set.database.tables.HighScore

/**
 * Created by Ernesto on 26/11/2017.
 */
class HighScoresAdapter(private val items: Array<HighScore>) : RecyclerView.Adapter<HighScoreViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.holder_highscore, parent, false)

        return HighScoreViewHolder(v)
    }

    override fun onBindViewHolder(holder: HighScoreViewHolder, position: Int) {
        holder.bind(position, items[position])
    }

    override fun getItemCount(): Int = items.size
}