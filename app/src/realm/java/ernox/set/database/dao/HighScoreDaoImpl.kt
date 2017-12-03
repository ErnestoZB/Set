package ernox.set.database.dao

import ernox.set.database.tables.HighScore
import io.realm.Realm
import io.realm.Sort

/**
 * Created by Ernesto on 02/12/2017.
 */
class HighScoreDaoImpl : HighScoreDao {

    override fun insert(obj: HighScore) {
        val realm: Realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        realm.insert(obj)
        realm.commitTransaction()

        realm.close()
    }

    override fun getItems(limit: Int): Array<HighScore> {
        val realm: Realm = Realm.getDefaultInstance()

        val results = realm.where(HighScore::class.java)
                           .findAllSorted("highScore", Sort.DESCENDING)

        val count = if(results.count() < 10) results.count() else limit
        val items = Array(count, {
            realm.copyFromRealm(results[it])!!
        })

        realm.close()

        return items
    }
}