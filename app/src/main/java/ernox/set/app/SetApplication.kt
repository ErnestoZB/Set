package ernox.set.app

import android.app.Application
import ernox.set.database.AppDatabase

/**
 * Created by Ernesto on 02/12/2017.
 */
class SetApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppDatabase.init(this)
    }
}