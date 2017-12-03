package ernox.set.database

import android.content.Context
import ernox.set.database.dao.HighScoreDao
import ernox.set.database.dao.HighScoreDaoImpl
import io.realm.Realm

/**
 * Created by Ernesto on 30/11/2017.
 */
class AppDatabase  {

    companion object : IDatabase{

        private val highScoreDao = HighScoreDaoImpl()
        override fun getHighScoreDao(): HighScoreDao {
            return highScoreDao
        }

        override fun init(context: Context) {
            Realm.init(context.applicationContext)
        }
    }
}