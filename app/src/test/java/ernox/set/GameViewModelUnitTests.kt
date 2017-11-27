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


class GameViewModelUnitTests {

    private lateinit var viewModel: GameViewModel

    @Before
    fun setUp() {
        viewModel = GameViewModel()
    }

    @Test
    fun should_FillDeck_ToPrepareGame() {

        // Act
        viewModel.onPrepareGame()

        // Assert
        assertEquals(81, viewModel.getDeck().cards.size)
    }

    @Test
    fun should_Have12CardsOnTable_When_GameStarts() {

        // Act
        viewModel.onPrepareGame()
        viewModel.onStartGame()

        assertEquals(12, viewModel.getTableCards().size)
    }

    @Test
    fun should_Have69CardsOnDeck_When_GameStarts() {

        // Act
        viewModel.onPrepareGame()
        viewModel.onStartGame()

        assertEquals(69, viewModel.getDeck().cards.size)
    }

    @Test
    fun should_SaveCard_When_CardIsSelected() {

        // Setup
        val figure = Figure(Symbol.TRIANGLE, Shading.OPEN, Color.GREEN)
        val card = Card(figure, 1)

        // Act
        viewModel.onCardSelected(card)

        // Assert
        assertTrue(viewModel.getSelectedCards().size > 0)
    }

    @Test
    fun should_clearSelectedCards_When_ThreeAreSelected() {

        // Act
        viewModel.onPrepareGame()
        viewModel.onStartGame()
        selectCard(1, Symbol.TRIANGLE, Shading.OPEN, Color.GREEN, 0)
        selectCard(2, Symbol.TRIANGLE, Shading.OPEN, Color.GREEN, 1)
        selectCard(3, Symbol.TRIANGLE, Shading.OPEN, Color.GREEN, 2)

        // Assert
        assertEquals(0, viewModel.getSelectedCards().size)
    }

    @Test
    fun should_showColorRuleNotSatisfiedError() {

        // Act
        viewModel.onPrepareGame()
        viewModel.onStartGame()
        selectCard(1, Symbol.TRIANGLE, Shading.OPEN, Color.GREEN, 0)
        selectCard(2, Symbol.TRIANGLE, Shading.OPEN, Color.RED, 1)
        selectCard(3, Symbol.TRIANGLE, Shading.OPEN, Color.GREEN, 2)

        // Assert
        assertEquals(R.string.rule_color, viewModel.errorMessageId.get())
    }

    @Test
    fun should_showShadingRuleNotSatisfiedError() {

        // Act
        viewModel.onPrepareGame()
        viewModel.onStartGame()
        selectCard(1, Symbol.TRIANGLE, Shading.SOLID, Color.GREEN, 0)
        selectCard(2, Symbol.TRIANGLE, Shading.OPEN, Color.GREEN, 1)
        selectCard(3, Symbol.TRIANGLE, Shading.OPEN, Color.GREEN, 2)

        // Assert
        assertEquals(R.string.rule_shading, viewModel.errorMessageId.get())
    }

    @Test
    fun should_showSymbolRuleNotSatisfiedError() {

        // Act
        viewModel.onPrepareGame()
        viewModel.onStartGame()
        selectCard(1, Symbol.OVAL, Shading.OPEN, Color.GREEN, 0)
        selectCard(2, Symbol.TRIANGLE, Shading.OPEN, Color.GREEN, 1)
        selectCard(3, Symbol.TRIANGLE, Shading.OPEN, Color.GREEN, 2)

        // Assert
        assertEquals(R.string.rule_symbol, viewModel.errorMessageId.get())
    }

    @Test
    fun should_showNumberRuleNotSatisfiedError() {

        // Act
        viewModel.onPrepareGame()
        viewModel.onStartGame()
        selectCard(2, Symbol.TRIANGLE, Shading.OPEN, Color.GREEN, 0)
        selectCard(2, Symbol.TRIANGLE, Shading.OPEN, Color.GREEN, 1)
        selectCard(3, Symbol.TRIANGLE, Shading.OPEN, Color.GREEN, 2)

        // Assert
        assertEquals(R.string.rule_number, viewModel.errorMessageId.get())
    }

    @Test
    fun should_IncreaseScore_When_ASetIsFound() {

        // Act
        viewModel.onPrepareGame()
        viewModel.onStartGame()
        selectCard(1, Symbol.TRIANGLE, Shading.OPEN, Color.GREEN, 0)
        selectCard(2, Symbol.OVAL, Shading.SOLID, Color.RED, 1)
        selectCard(3, Symbol.SQUARE, Shading.STRIPED, Color.BLUE, 2)

        // Assert
        assertEquals(1, viewModel.getScore())
    }

    @Test
    fun should_PutThreeNewCardsInTable_When_ASetIsFound() {

        // Act
        viewModel.onPrepareGame()
        viewModel.onStartGame()
        selectCard(1, Symbol.TRIANGLE, Shading.OPEN, Color.GREEN, 0)
        selectCard(2, Symbol.OVAL, Shading.SOLID, Color.RED, 1)
        selectCard(3, Symbol.SQUARE, Shading.STRIPED, Color.BLUE, 2)

        // Assert
        assertEquals(66, viewModel.getDeck().cards.size)
    }

    private fun selectCard(number: Int,
                           symbol: Symbol,
                           shading: Shading,
                           color: Color,
                           tablePosition: Int) {

        val figure = Figure(symbol, shading, color)

        val card = Card(figure, number.toShort())

        viewModel.getTableCards()[tablePosition] = card

        viewModel.onCardSelected(card)
    }
}