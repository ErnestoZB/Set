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
    fun should_Have12CardsOnTable_When_lGameStarts() {

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
        val figure = Figure(Symbol.DIAMOND, Shading.OPEN, Color.GREEN)
        val card = Card(figure, 1)

        // Act
        viewModel.onCardSelected(card)

        // Assert
        assertTrue(viewModel.getSelectedCards().size > 0)
    }

    @Test
    fun should_clearSelectedCards_When_ThreeAreSelected() {

        // Setup
        val figure = Figure(Symbol.DIAMOND, Shading.OPEN, Color.GREEN)
        val card1 = Card(figure, 1)
        val card2 = Card(figure, 2)
        val card3 = Card(figure, 3)

        // Act
        viewModel.onCardSelected(card1)
        viewModel.onCardSelected(card2)
        viewModel.onCardSelected(card3)

        // Assert
        assertEquals(0, viewModel.getSelectedCards().size)
    }

    @Test
    fun should_showColorRuleNotSatisfiedError() {

        // Setup
        val figure1 = Figure(Symbol.DIAMOND, Shading.OPEN, Color.GREEN)
        val figure2 = Figure(Symbol.DIAMOND, Shading.OPEN, Color.RED)
        val figure3 = Figure(Symbol.DIAMOND, Shading.OPEN, Color.GREEN)

        val card1 = Card(figure1, 1)
        val card2 = Card(figure2, 2)
        val card3 = Card(figure3, 3)

        // Act
        viewModel.onCardSelected(card1)
        viewModel.onCardSelected(card2)
        viewModel.onCardSelected(card3)

        // Assert
        assertEquals(R.string.rule_color, viewModel.errorMessageId.get())
    }

    @Test
    fun should_showShadingRuleNotSatisfiedError() {

        // Setup
        val figure1 = Figure(Symbol.DIAMOND, Shading.SOLID, Color.GREEN)
        val figure2 = Figure(Symbol.DIAMOND, Shading.OPEN, Color.GREEN)
        val figure3 = Figure(Symbol.DIAMOND, Shading.OPEN, Color.GREEN)

        val card1 = Card(figure1, 1)
        val card2 = Card(figure2, 2)
        val card3 = Card(figure3, 3)

        // Act
        viewModel.onCardSelected(card1)
        viewModel.onCardSelected(card2)
        viewModel.onCardSelected(card3)

        // Assert
        assertEquals(R.string.rule_shading, viewModel.errorMessageId.get())
    }

    @Test
    fun should_showSymbolRuleNotSatisfiedError() {

        // Setup
        val figure1 = Figure(Symbol.OVAL, Shading.OPEN, Color.GREEN)
        val figure2 = Figure(Symbol.DIAMOND, Shading.OPEN, Color.GREEN)
        val figure3 = Figure(Symbol.DIAMOND, Shading.OPEN, Color.GREEN)

        val card1 = Card(figure1, 1)
        val card2 = Card(figure2, 2)
        val card3 = Card(figure3, 3)

        // Act
        viewModel.onCardSelected(card1)
        viewModel.onCardSelected(card2)
        viewModel.onCardSelected(card3)

        // Assert
        assertEquals(R.string.rule_symbol, viewModel.errorMessageId.get())
    }

    @Test
    fun should_showNumberRuleNotSatisfiedError() {

        // Setup
        val figure1 = Figure(Symbol.DIAMOND, Shading.OPEN, Color.GREEN)
        val figure2 = Figure(Symbol.DIAMOND, Shading.OPEN, Color.GREEN)
        val figure3 = Figure(Symbol.DIAMOND, Shading.OPEN, Color.GREEN)

        val card1 = Card(figure1, 2)
        val card2 = Card(figure2, 2)
        val card3 = Card(figure3, 3)

        // Act
        viewModel.onCardSelected(card1)
        viewModel.onCardSelected(card2)
        viewModel.onCardSelected(card3)

        // Assert
        assertEquals(R.string.rule_number, viewModel.errorMessageId.get())
    }
}
