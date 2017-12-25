package ernox.set.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import ernox.set.database.dao.HighScoreDao
import ernox.set.database.tables.HighScore
import ernox.set.enums.Color
import ernox.set.enums.SetError
import ernox.set.enums.Shading
import ernox.set.enums.Symbol
import ernox.set.models.Card
import ernox.set.models.Deck
import ernox.set.models.Figure
import ernox.set.models.Set
import org.jetbrains.anko.doAsync
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Ernesto on 25/11/2017.
 */
class GameViewModel : ViewModel() {

    private lateinit var highScoreDao: HighScoreDao
    fun setHighScoreDao(highScoreDao: HighScoreDao) {
        this.highScoreDao = highScoreDao
    }

    private val deck: Deck = Deck()
    fun getDeck(): Deck = deck

    private val tableCards: ObservableField<ArrayList<Card?>> = ObservableField(arrayListOf())
    fun getTableCards() : ObservableField<ArrayList<Card?>> = tableCards

    private var updateTable: MutableLiveData<Boolean> = MutableLiveData()
    fun shouldUpdateTable(): LiveData<Boolean> = updateTable

    private val selectedCards: ArrayList<Card> = ArrayList(3)
    fun getSelectedCards(): ArrayList<Card> = selectedCards

    var score : ObservableField<Long> = ObservableField(0)

    var setsDone : ObservableField<Int> = ObservableField(0)

    private var errorMessageId : MutableLiveData<Int> = MutableLiveData()
    fun getErrorMessageId() : LiveData<Int> = errorMessageId

    fun onRestartGame() {

        saveScoreInDatabase()

        restartGameValues()

        onStartGame()

        updateTable.value = true
    }

    private fun saveScoreInDatabase() {
        val points = score.get()

        if(points > 0) {
            val highScore = HighScore(points)

            doAsync {
                highScoreDao.insert(highScore)
            }
        }
    }

    private fun restartGameValues() {
        score.set(0)
        setsDone.set(0)
        clearSelectedCards()
        clearHintCards()
        tableCards.notifyChange()
    }

    fun onStartGame() {
        fillDeck()
        shuffleDeck()
        putCardsOnTable()
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

        deck.cards = cards
    }

    private fun shuffleDeck() {
        deck.shuffle()
    }

    private fun putCardsOnTable() {

        tableCards.get().clear()

        for(number in 1..12)
            deck.removeCard()?.let { tableCards.get().add(it) }
    }

    fun onCardSelected(card: Card) {
        addToSelectedCards(card)

        if(selectedCards.size == 3)
        {
            val setError = areSelectedCardsASet(selectedCards[0], selectedCards[1], selectedCards[2])

            if(setError == SetError.NO_ERROR)
            {
                increaseSetsDone()

                increaseScore()

                putNewCardsInTable()
            }
            else
                errorMessageId.value = setError.errorId


            clearSelectedCards()

            updateTable.value = true
        }
    }

    private fun clearSelectedCards() {

        for(card in selectedCards)
            card.isSelected = false

        selectedCards.clear()
    }

    private fun addToSelectedCards(card: Card) {
        card.isSelected = true

        if(selectedCards.indexOf(card) == -1)
            selectedCards.add(card)
    }

    private fun putNewCardsInTable() {

        for(card in selectedCards) {
            val position = tableCards.get().indexOf(card)

            tableCards.get()[position] = deck.removeCard()
        }
    }

    private fun areSelectedCardsASet(card1: Card, card2: Card, card3: Card) : SetError {

        if(!isColorRuleSatisfied(card1, card2, card3))
            return SetError.COLOR

        if(!isShadingRuleSatisfied(card1, card2, card3))
            return SetError.SHADING

        if(!isSymbolRuleSatisfied(card1, card2, card3))
            return SetError.SYMBOL

        if(!isNumberRulesSatisfied(card1, card2, card3))
            return SetError.NUMBER

        return SetError.NO_ERROR
    }

    private fun isNumberRulesSatisfied(card1: Card, card2: Card, card3: Card): Boolean {
        val number1 = card1.numberOfFigures
        val number2 = card2.numberOfFigures
        val number3 = card3.numberOfFigures

        return number1 == number2 && number2 == number3 || number1 != number2 && number2 != number3 && number3 != number1
    }

    private fun isColorRuleSatisfied(card1: Card, card2: Card, card3: Card): Boolean {

        val color1 = card1.figure.color
        val color2 = card2.figure.color
        val color3 = card3.figure.color

        return color1 == color2 && color2 == color3 || color1 != color2 && color2 != color3 && color3 != color1
    }

    private fun isShadingRuleSatisfied(card1: Card, card2: Card, card3: Card): Boolean {

        val shading1 = card1.figure.shading
        val shading2 = card2.figure.shading
        val shading3 = card3.figure.shading

        return shading1 == shading2 && shading2 == shading3 || shading1 != shading2 && shading2 != shading3 && shading3 != shading1
    }

    private fun isSymbolRuleSatisfied(card1: Card, card2: Card, card3: Card): Boolean {

        val symbol1 = card1.figure.symbol
        val symbol2 = card2.figure.symbol
        val symbol3 = card3.figure.symbol

        return symbol1 == symbol2 && symbol2 == symbol3 || symbol1 != symbol2 && symbol2 != symbol3 && symbol3 != symbol1
    }

    private fun increaseSetsDone() {
        setsDone.set( setsDone.get() + 1)
    }

    private fun increaseScore() {
        score.set( score.get() + 10)
    }

    private fun clearHintCards() {
        for (card in selectedCards) {
            card.isHint = false
        }
    }

    private fun foundAvailableSet(): Set? {

        val tableCardsSize = tableCards.get().size

        for (x in 0 until tableCardsSize - 2)
            for (y in x + 1 until tableCardsSize - 1)
                for (z in y + 1 until tableCardsSize)
                {
                    val card1 = tableCards.get()[x]
                    val card2 = tableCards.get()[y]
                    val card3 = tableCards.get()[z]

                    if (card1 != null && card2 != null && card3 != null)
                    {
                        val isASet = areSelectedCardsASet(card1, card2, card3)

                        if (isASet == SetError.NO_ERROR)
                        {
                            clearSelectedCards()
                            clearHintCards()

                            return Set(card1, card2, card3)
                        }
                    }
                }
        return null
    }

    fun onShowHint() {

        val set = foundAvailableSet()

        set?.let { showSetAsHint(set) }
    }

    private fun showSetAsHint(set: Set) {

        set.card1.isHint = true
        set.card2.isHint = true
        set.card3.isHint = true

        updateTable.value = true
    }
}