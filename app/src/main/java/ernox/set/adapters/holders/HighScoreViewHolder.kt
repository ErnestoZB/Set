package ernox.set.adapters.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ernox.set.R
import ernox.set.database.tables.HighScore


/**
 * Created by Ernesto on 26/11/2017.
 */
class HighScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(position: Int, highScore: HighScore) {


        itemView.findViewById<TextView>(R.id.position).text = (position + 1).toString() + "."

        itemView.findViewById<TextView>(R.id.score).text = highScore.highScore.toString() + " points"
    }

}