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


    fun getDrawableName() : String {

        val nameBuilder = StringBuilder()

        nameBuilder.append("ic_")
                   .append(color.name).append("_")
                   .append(symbol.name).append("_")
                   .append(shading.name)
                   .append(".xml")

        return nameBuilder.toString().toLowerCase()
    }
}