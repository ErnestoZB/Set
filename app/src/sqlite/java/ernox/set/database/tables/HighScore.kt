package ernox.set.database.tables

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Ernesto on 30/11/2017.
 */
@Entity(tableName = "highScores",
        indices = arrayOf(Index(value = "highScore", name = "highScore")))
data class HighScore(val highScore: Long) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
