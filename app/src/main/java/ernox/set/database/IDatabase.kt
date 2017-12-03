package ernox.set.database

import android.content.Context
import ernox.set.database.dao.HighScoreDao

/**
 * Created by Ernesto on 02/12/2017.
 */
interface IDatabase {
    fun init(context: Context)

    fun getHighScoreDao() : HighScoreDao
}