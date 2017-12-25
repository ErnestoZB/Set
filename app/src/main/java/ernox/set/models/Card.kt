package ernox.set.models

/**
 * Created by Ernesto on 25/11/2017.
 */
data class Card(val figure: Figure,
                val numberOfFigures: Short,
                var isSelected: Boolean = false,
                var isHint: Boolean = false) {

    fun getDrawableName() : String {

        val nameBuilder = StringBuilder()

        nameBuilder.append("ic_")
                   .append(getNumberAsText(numberOfFigures.toInt())).append("_")
                   .append(figure.getName())

        return nameBuilder.toString().toLowerCase()
    }

    private fun getNumberAsText(number : Int) : String {
        return when(number) {
            1 -> "one"
            2 -> "two"
            3 -> "three"
            else -> ""
        }
    }
}


