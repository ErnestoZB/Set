package ernox.set.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ernox.set.R
import ernox.set.enums.Color
import ernox.set.enums.Shading
import ernox.set.enums.Symbol
import ernox.set.models.Card
import ernox.set.models.Deck
import ernox.set.models.Figure
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Ernesto on 25/11/2017.
 */
class GameViewModel : ViewModel() {

    private lateinit var deck: Deck
    fun getDeck(): Deck = deck

    private lateinit var tableCards: ArrayList<Card>
    fun getTableCards() : ArrayList<Card> = tableCards

    private var updateTable: MutableLiveData<Boolean> = MutableLiveData()
    fun shouldUpdateTable(): LiveData<Boolean> = updateTable

    private val selectedCards: ArrayList<Card> = ArrayList(3)
    fun getSelectedCards(): ArrayList<Card> = selectedCards

    private var score = 0
    fun getScore(): Int = score

    private var errorMessageId : MutableLiveData<Int> = MutableLiveData()
    fun getErrorMessageId() : LiveData<Int> = errorMessageId

    private var clearCardsBackground : MutableLiveData<Boolean> = MutableLiveData()
    fun shouldClearCardsBackground() : LiveData<Boolean> = clearCardsBackground

    fun onPrepareGame() {
        fillDeck()
        shuffleDeck()
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

        val cards: ArrayList<Card> = ArrayList()

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

    private fun shuffleDeck() {
        val seed = System.nanoTime()
        Collections.shuffle(deck.cards, Random(seed))
    }

    fun onCardSelected(card: Card) {
        addToSelectedCards(card)

        if(selectedCards.size == 3)
        {
            if(areSelectedCardsASet())
            {
                increaseScore()

                putNewCardsInTable()
            }

            selectedCards.clear()

            clearCardsBackground.value = true
        }
    }

    private fun addToSelectedCards(card: Card) {
        selectedCards.add(card)
    }

    private fun putNewCardsInTable() {

        for(card in selectedCards) {
            val position = tableCards.indexOf(card)

            deck.removeCard()?.let { tableCards.set(position!!, it) }
        }

        updateTable.value = true
    }

    private fun areSelectedCardsASet() : Boolean {

        if(!isColorRuleSatisfied()) {
            errorMessageId.value = R.string.rule_color
            return false
        }

        if(!isShadingRuleSatisfied()) {
            errorMessageId.value = R.string.rule_shading
            return false
        }

        if(!isSymbolRuleSatisfied()) {
            errorMessageId.value = R.string.rule_symbol
            return false
        }

        if(!isNumberRulesSatisfied()) {
            errorMessageId.value = R.string.rule_number
            return false
        }

        return true
    }

    private fun isNumberRulesSatisfied(): Boolean {
        val number1 = selectedCards[0].numberOfFigures
        val number2 = selectedCards[1].numberOfFigures
        val number3 = selectedCards[2].numberOfFigures

        return number1 == number2 && number2 == number3 || number1 != number2 && number2 != number3 && number3 != number1
    }

    private fun isColorRuleSatisfied(): Boolean {

        val color1 = selectedCards[0].figure.color
        val color2 = selectedCards[1].figure.color
        val color3 = selectedCards[2].figure.color

        return color1 == color2 && color2 == color3 || color1 != color2 && color2 != color3 && color3 != color1
    }

    private fun isShadingRuleSatisfied(): Boolean {

        val shading1 = selectedCards[0].figure.shading
        val shading2 = selectedCards[1].figure.shading
        val shading3 = selectedCards[2].figure.shading

        return shading1 == shading2 && shading2 == shading3 || shading1 != shading2 && shading2 != shading3 && shading3 != shading1
    }

    private fun isSymbolRuleSatisfied(): Boolean {

        val symbol1 = selectedCards[0].figure.symbol
        val symbol2 = selectedCards[1].figure.symbol
        val symbol3 = selectedCards[2].figure.symbol

        return symbol1 == symbol2 && symbol2 == symbol3 || symbol1 != symbol2 && symbol2 != symbol3 && symbol3 != symbol1
    }

    private fun increaseScore() {
        score++
    }
}