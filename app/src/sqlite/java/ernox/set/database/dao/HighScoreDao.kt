package ernox.set.database.dao

import android.arch.persistence.room.*
import ernox.set.database.tables.HighScore

/**
 * Created by Ernesto on 30/11/2017.
 */
@Dao
interface HighScoreDao {
    @Insert
    fun insert(obj: HighScore)

    @Query("SELECT * FROM highScores ORDER BY highScore DESC LIMIT :count")
    fun GetItems(count: Int): Array<HighScore>
}