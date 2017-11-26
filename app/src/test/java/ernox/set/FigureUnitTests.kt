package ernox.set

import ernox.set.enums.Color
import ernox.set.enums.Shading
import ernox.set.enums.Symbol
import ernox.set.models.Figure
import org.junit.Test

import org.junit.Assert.*


class FigureUnitTests {

    @Test
    fun should_showCorrectImageName_When_FigureIsTriangleOpenGreen() {

        // Setup
        val figure = Figure(Symbol.TRIANGLE, Shading.OPEN, Color.GREEN)

        // Assert
        assertEquals("ic_green_triangle_open.xml", figure.getDrawableName())
    }

    @Test
    fun should_showCorrectImageName_When_FigureIsSquareStripedRed() {

        // Setup
        val figure = Figure(Symbol.SQUARE, Shading.STRIPED, Color.RED)

        // Assert
        assertEquals("ic_red_square_striped.xml", figure.getDrawableName())
    }

    @Test
    fun should_showCorrectImageName_When_FigureIsOvalSolidBlue() {

        // Setup
        val figure = Figure(Symbol.OVAL, Shading.SOLID, Color.BLUE)

        // Assert
        assertEquals("ic_blue_oval_solid.xml", figure.getDrawableName())
    }
}
