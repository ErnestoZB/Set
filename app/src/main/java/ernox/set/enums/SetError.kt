package ernox.set.enums

import ernox.set.R

/**
 * Created by Ernesto on 03/12/2017.
 */
enum class SetError(val errorId: Int) {
    NO_ERROR(0),
    COLOR(R.string.rule_color),
    SHADING(R.string.rule_shading),
    SYMBOL(R.string.rule_symbol),
    NUMBER(R.string.rule_number)
}