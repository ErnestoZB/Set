package ernox.set.models

import java.util.*

/**
 * Created by Ernesto on 25/11/2017.
 */
data class Deck(val cards: Array<Card>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Deck

        if (!Arrays.equals(cards, other.cards)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(cards)
    }
}