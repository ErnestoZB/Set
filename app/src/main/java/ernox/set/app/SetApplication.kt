package ernox.set.app

import android.app.Application
import ernox.set.database.AppDatabase
import ernox.set.di.components.DaggerSingletonComponent
import ernox.set.di.components.SingletonComponent
import ernox.set.di.modules.AppModule
import ernox.set.di.modules.DatabaseModule

/**
 * Created by Ernesto on 02/12/2017.
 */
class SetApplication : Application() {

    private val component: SingletonComponent by lazy {
        DaggerSingletonComponent.builder()
                                .appModule(AppModule(this))
                                .databaseModule(DatabaseModule())
                                .build()
    }

    override fun onCreate() {
        super.onCreate()

        AppDatabase.init(this)
    }

    fun getAppComponent() = component
}