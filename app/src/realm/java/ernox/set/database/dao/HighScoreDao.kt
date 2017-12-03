package ernox.set.database.dao

import ernox.set.database.tables.HighScore

/**
 * Created by Ernesto on 30/11/2017.
 */
interface HighScoreDao {
    fun insert(obj: HighScore)

    fun getItems(count: Int): Array<HighScore>
}