package ernox.set.models

import ernox.set.enums.Color
import ernox.set.enums.Shading
import ernox.set.enums.Symbol

/**
 * Created by Ernesto on 25/11/2017.
 */
data class Figure(val symbol: Symbol,
                  val shading: Shading,
                  val color: Color) {

    fun getName() : String {

        val nameBuilder = StringBuilder()

        nameBuilder.append(color.name).append("_")
                   .append(symbol.name).append("_")
                   .append(shading.name)

        return nameBuilder.toString().toLowerCase()
    }
}