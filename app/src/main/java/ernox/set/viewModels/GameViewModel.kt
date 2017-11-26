package ernox.set.viewModels

import android.arch.lifecycle.ViewModel
import ernox.set.enums.Color
import ernox.set.enums.Shading
import ernox.set.enums.Symbol
import ernox.set.models.Card
import ernox.set.models.Deck
import ernox.set.models.Figure

/**
 * Created by Ernesto on 25/11/2017.
 */
class GameViewModel : ViewModel() {

    private lateinit var deck: Deck
    fun getDeck(): Deck {
        return deck
    }

    private lateinit var tableCards: ArrayList<Card>
    fun getTableCards(): ArrayList<Card> {
        return tableCards
    }

    private val selectedCards: ArrayList<Card> = ArrayList(3)
    fun getSelectedCards(): ArrayList<Card> {
        return selectedCards
    }


    fun onPrepareGame() {
        fillDeck()
    }

    fun onStartGame() {
        putCardsOnTable()
    }

    private fun putCardsOnTable() {
        tableCards = ArrayList()

        for(number in 1..12)
            deck.removeCard()?.let { tableCards.add(it) }
    }

    private fun fillDeck() {

        var cards: ArrayList<Card> = ArrayList()

        for (number in 1..3)
            for(color in Color.values())
                for(shading in Shading.values())
                    for(symbol in Symbol.values())
                    {
                        val figure = Figure(symbol, shading, color)
                        val card = Card(figure,  number.toShort())

                        cards.add(card)
                    }

        deck = Deck(cards)
    }

    fun onCardSelected(card: Card) {
        addToSelectedCards(card)
    }

    private fun addToSelectedCards(card: Card) {
        selectedCards.add(card)

        if(selectedCards.size == 3)
        {
            areSelectedCardsASet()

            selectedCards.clear()
        }
    }

    private fun areSelectedCardsASet() {

    }
}