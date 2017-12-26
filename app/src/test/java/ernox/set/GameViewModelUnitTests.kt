package ernox.set

import ernox.set.enums.Color
import ernox.set.enums.Shading
import ernox.set.enums.Symbol
import ernox.set.models.Card
import ernox.set.models.Figure
import ernox.set.viewModels.GameViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import android.arch.core.executor.testing.InstantTaskExecutorRule
import ernox.set.database.dao.HighScoreDao
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.MockitoAnnotations

class GameViewModelUnitTests {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var highScoreDao: HighScoreDao

    private lateinit var viewModel: GameViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = GameViewModel()
        viewModel.setHighScoreDao(highScoreDao)
    }

    @Test
    fun should_Have12CardsOnTable_When_GameStarts() {

        // Act
        viewModel.onStartGame()

        assertEquals(12, viewModel.getTableCards().get().size)
    }

    @Test
    fun should_Have69CardsOnDeck_When_GameStarts() {

        // Act
        viewModel.onStartGame()

        assertEquals(69, viewModel.getDeck().cards.size)
    }

    @Test
    fun should_SaveCard_When_CardIsSelected() {

        // Setup
        val figure = Figure(Symbol.DIAMOND, Shading.OPEN, Color.GREEN)
        val card = Card(figure, 1)

        // Act
        viewModel.onCardSelected(card)

        // Assert
        assertTrue(viewModel.getSelectedCards().size > 0)
    }

    @Test
    fun should_clearSelectedCards_When_ThreeAreSelected() {

        // Act
        viewModel.onStartGame()
        selectCard(1, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 0)
        selectCard(2, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 1)
        selectCard(3, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 2)

        // Assert
        assertEquals(0, viewModel.getSelectedCards().size)
    }

    @Test
    fun should_clearSelectedCardsBackground_When_ThreeAreSelected() {

        // Act
        viewModel.onStartGame()
        val card1 = selectCard(1, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 0)
        val card2 = selectCard(2, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 1)
        val card3 = selectCard(3, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 2)

        // Assert
        assertFalse(card1.isSelected)
        assertFalse(card2.isSelected)
        assertFalse(card3.isSelected)

    }

    @Test
    fun should_showColorRuleNotSatisfiedError() {

        // Act
        viewModel.onStartGame()
        selectCard(1, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 0)
        selectCard(2, Symbol.DIAMOND, Shading.OPEN, Color.RED, 1)
        selectCard(3, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 2)

        // Assert
        assertEquals(R.string.rule_color, viewModel.getErrorMessageId().value!!)
    }

    @Test
    fun should_showShadingRuleNotSatisfiedError() {

        // Act
        viewModel.onStartGame()
        selectCard(1, Symbol.DIAMOND, Shading.SOLID, Color.GREEN, 0)
        selectCard(2, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 1)
        selectCard(3, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 2)

        // Assert
        assertEquals(R.string.rule_shading, viewModel.getErrorMessageId().value!!)
    }

    @Test
    fun should_showSymbolRuleNotSatisfiedError() {

        // Act
        viewModel.onStartGame()
        selectCard(1, Symbol.OVAL, Shading.OPEN, Color.GREEN, 0)
        selectCard(2, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 1)
        selectCard(3, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 2)

        // Assert
        assertEquals(R.string.rule_symbol, viewModel.getErrorMessageId().value!!)
    }

    @Test
    fun should_showNumberRuleNotSatisfiedError() {

        // Act
        viewModel.onStartGame()
        selectCard(2, Symbol.OVAL, Shading.OPEN, Color.GREEN, 0)
        selectCard(2, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 1)
        selectCard(3, Symbol.SQUIGGLE, Shading.OPEN, Color.GREEN, 2)

        // Assert
        assertEquals(R.string.rule_number, viewModel.getErrorMessageId().value!!)
    }

    @Test
    fun should_IncreaseScore_When_ASetIsFound() {

        // Act
        viewModel.onStartGame()
        selectCard(1, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 0)
        selectCard(2, Symbol.OVAL, Shading.SOLID, Color.RED, 1)
        selectCard(3, Symbol.SQUIGGLE, Shading.STRIPED, Color.PURPLE, 2)

        // Assert
        assertEquals(10, viewModel.score.get())
    }

    @Test
    fun should_IncreaseSetsDone_When_ASetIsFound() {

        // Act
        viewModel.onStartGame()
        selectCard(1, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 0)
        selectCard(2, Symbol.OVAL, Shading.SOLID, Color.RED, 1)
        selectCard(3, Symbol.SQUIGGLE, Shading.STRIPED, Color.PURPLE, 2)

        // Assert
        assertEquals(1, viewModel.setsDone.get())
    }

    @Test
    fun should_PutThreeNewCardsInTable_When_ASetIsFound() {

        // Act
        viewModel.onStartGame()
        selectCard(1, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 0)
        selectCard(2, Symbol.OVAL, Shading.SOLID, Color.RED, 1)
        selectCard(3, Symbol.SQUIGGLE, Shading.STRIPED, Color.PURPLE, 2)

        // Assert
        assertEquals(66, viewModel.getDeck().cards.size)
    }

    @Test
    fun should_NotSelectACard_If_ItIsAlreadySelected() {

        // Act
        viewModel.onStartGame()
        selectCard(1, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 0)
        selectCard(1, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 0)
        selectCard(1, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 0)

        // Assert
        assertEquals(1, viewModel.getSelectedCards().size)
    }

    @Test
    fun should_SetScoreToZero_OnGameRestart() {

        // Setup
        should_IncreaseScore_When_ASetIsFound()

        async(UI) {
            // Act
            viewModel.onRestartGame()

            // Assert
            assertEquals(0, viewModel.score.get())
        }
    }

    @Test
    fun should_SetSetsDoneToZero_OnGameRestart() {

        // Setup
        should_IncreaseSetsDone_When_ASetIsFound()

        async(UI) {
            // Act
            viewModel.onRestartGame()

            // Assert
            assertEquals(0, viewModel.setsDone.get())
        }
    }

    @Test
    fun should_ClearSelectedCards_OnGameRestart() {

        // Setup
        viewModel.onStartGame()
        selectCard(1, Symbol.DIAMOND, Shading.OPEN, Color.GREEN, 0)
        selectCard(1, Symbol.SQUIGGLE, Shading.OPEN, Color.GREEN, 0)

        async(UI) {
            // Act
            viewModel.onRestartGame()

            // Assert
            assertEquals(0, viewModel.getSelectedCards().size)
        }
    }

    @Test
    fun should_SaveScoreInDatabase_If_ScoreGreaterThanZero() {

        // Setup
        viewModel.score.set(10)

        async(UI) {
            // Act
            viewModel.onRestartGame()

            Mockito.verify(highScoreDao).insert(any())
        }

    }

    @Test
    fun should_NotSaveScoreInDatabase_If_ScoreIsZero() {

        // Setup
        viewModel.score.set(0)

        async(UI) {
            // Act
            viewModel.onRestartGame()

            Mockito.verify(highScoreDao, never()).insert(any())
        }
    }

    @Test
    fun should_Have12CardsOnTable_When_GameRestarts() {

        async(UI) {
            // Act
            viewModel.onRestartGame()

            assertEquals(12, viewModel.getTableCards().get().size)
        }
    }

    @Test
    fun should_Have69CardsOnDeck_When_GameRestarts() {

        async(UI) {
            // Act
            viewModel.onRestartGame()

            assertEquals(69, viewModel.getDeck().cards.size)
        }
    }

    private fun selectCard(number: Int,
                           symbol: Symbol,
                           shading: Shading,
                           color: Color,
                           tablePosition: Int) : Card {

        val figure = Figure(symbol, shading, color)

        val card = Card(figure, number.toShort())

        viewModel.getTableCards().get()[tablePosition] = card

        viewModel.onCardSelected(card)

        return card
    }

    protected fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    protected fun <T> uninitialized(): T = null as T
}