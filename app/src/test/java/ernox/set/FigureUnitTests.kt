package ernox.set

import ernox.set.enums.Color
import ernox.set.enums.Shading
import ernox.set.enums.Symbol
import ernox.set.models.Figure
import org.junit.Test

import org.junit.Assert.*


class FigureUnitTests {

    @Test
    fun should_showCorrectImageName_When_FigureIsDiamondOpenGreen() {

        // Setup
        val figure = Figure(Symbol.DIAMOND, Shading.OPEN, Color.GREEN)

        // Assert
        assertEquals("green_diamond_open", figure.getName())
    }

    @Test
    fun should_showCorrectImageName_When_FigureIsSquiggleStripedRed() {

        // Setup
        val figure = Figure(Symbol.SQUIGGLE, Shading.STRIPED, Color.RED)

        // Assert
        assertEquals("red_squiggle_striped", figure.getName())
    }

    @Test
    fun should_showCorrectImageName_When_FigureIsOvalSolidPurple() {

        // Setup
        val figure = Figure(Symbol.OVAL, Shading.SOLID, Color.PURPLE)

        // Assert
        assertEquals("purple_oval_solid", figure.getName())
    }
}
