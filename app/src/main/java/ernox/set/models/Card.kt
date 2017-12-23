package ernox.set.models

/**
 * Created by Ernesto on 25/11/2017.
 */
data class Card(val figure: Figure,
                val numberOfFigures: Short,
                var isSelected: Boolean = false,
                var isHint: Boolean = false)