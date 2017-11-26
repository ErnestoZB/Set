package ernox.set.models

import kotlin.collections.ArrayList

/**
 * Created by Ernesto on 25/11/2017.
 */
data class Deck(val cards: ArrayList<Card>) {

    fun removeCard() : Card? {
        var card: Card? = null

        if(!cards.isEmpty())
            card = cards.removeAt(0)

        return card
    }
}